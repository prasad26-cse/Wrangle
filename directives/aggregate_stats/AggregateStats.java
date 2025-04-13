package directives.aggregate_stats;

import parser.ByteSize;
import parser.TimeDuration;
import parser.TokenType;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.parser.UsageDefinition;
import io.cdap.wrangler.api.parser.Token;
import io.cdap.wrangler.api.parser.TokenGroup;

import java.util.Collections;
import java.util.List;

/**
 * AggregateStats Directive: Accumulates byte size and time duration columns.
 */
public class AggregateStats implements Directive {
  private String byteSizeColumn;
  private String timeDurationColumn;
  private String outputSizeColumn;
  private String outputTimeColumn;

  private long totalBytes = 0;
  private long totalNanos = 0;

  @Override
  public UsageDefinition define() {
    return UsageDefinition.builder("aggregate-stats")
      .define("byteSizeCol", TokenType.BYTE_SIZE)
      .define("timeDurationCol", TokenType.TIME_DURATION)
      .define("outputSizeCol", TokenType.COLUMN_NAME)
      .define("outputTimeCol", TokenType.COLUMN_NAME)
      .build();
  }

  @Override
  public void initialize(ExecutorContext ctx, TokenGroup args) {
    this.byteSizeColumn = ((Token) args.get("byteSizeCol")).value();
    this.timeDurationColumn = ((Token) args.get("timeDurationCol")).value();
    this.outputSizeColumn = ((Token) args.get("outputSizeCol")).value();
    this.outputTimeColumn = ((Token) args.get("outputTimeCol")).value();
  }

  @Override
  public List<Row> execute(List<Row> rows) {
    for (Row row : rows) {
      Object sizeValue = row.getValue(byteSizeColumn);
      Object timeValue = row.getValue(timeDurationColumn);

      if (sizeValue != null) {
        ByteSize size = new ByteSize(sizeValue.toString());
        totalBytes += size.getBytes();
      }

      if (timeValue != null) {
        TimeDuration time = new TimeDuration(timeValue.toString());
        totalNanos += time.getNanos();
      }
    }

    double totalMB = totalBytes / (1024.0 * 1024.0);
    double totalSeconds = totalNanos / 1_000_000_000.0;

    return Collections.singletonList(new Row(outputSizeColumn, totalMB, outputTimeColumn, totalSeconds));
  }
}
