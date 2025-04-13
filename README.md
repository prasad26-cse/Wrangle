# Wrangler Enhancement: Byte Size and Time Duration Parsers

This enhancement adds native support to the CDAP Wrangler library for parsing and utilizing byte size and time duration units within recipes. This includes new lexer tokens, updated parsing logic, extended API, and a new aggregate directive (`aggregate-stats`) to demonstrate the usage.

## 1. New Parsers Usage

This section describes how to use the new byte size and time duration parsers within Wrangler recipes.

### 1.1 Byte Size Parser

The Wrangler now understands common byte size units such as KB, MB, GB, TB, PB, EB, ZB, and YB (case-insensitive). You can directly use these units as arguments to directives where a size value is expected. The parsed value will be internally represented in bytes.

**Examples:**
``` set-column new_size = '10MB'
derive new_threshold = if(column_size > '1GB', 'large', 'small')
```
### 1.2 Time Duration Parser

Similarly, Wrangler now supports common time duration units like ms (milliseconds), s (seconds), min (minutes), h (hours), and d (days) (case-insensitive). These units can be used as arguments to directives that handle time intervals. The parsed value will be internally represented in nanoseconds.

**Examples:**
``` set-column timeout = '30s'
derive process_long = if(processing_time > '5min', 'true', 'false')
```
### 1.3 `aggregate-stats` Directive

A new aggregate directive, `aggregate-stats`, is introduced to demonstrate the usage of these new parsers. This directive allows you to calculate the total or average of byte size and time duration columns.

**Usage:**
```
aggregate-stats :<size_column> :<time_column> <target_size_column> <target_time_column> [GROUP BY <group_column(s)>] [OUTPUT_SIZE_UNIT <unit>] [OUTPUT_TIME_UNIT <unit>] [AGGREGATE_TYPE <type>]
```
**Arguments:**

* `<size_column>`: Name of the column containing byte size values.
* `<time_column>`: Name of the column containing time duration values.
* `<target_size_column>`: Name of the new column to store the aggregated size.
* `<target_time_column>`: Name of the new column to store the aggregated time.
* `GROUP BY <group_column(s)>`: (Optional) Specifies columns to group the aggregation by.
* `OUTPUT_SIZE_UNIT <unit>`: (Optional) Specifies the output unit for the total/average size (e.g., `MB`, `GB`). Defaults to bytes.
* `OUTPUT_TIME_UNIT <unit>`: (Optional) Specifies the output unit for the total/average time (e.g., `s`, `min`). Defaults to nanoseconds.
* `AGGREGATE_TYPE <type>`: (Optional) Specifies the aggregation type (`TOTAL` or `AVERAGE`). Defaults to `TOTAL`.

**Examples:**

1.  Calculate the total data transfer size in MB and the total response time in seconds for the entire dataset:

    ```
    aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec OUTPUT_SIZE_UNIT MB OUTPUT_TIME_UNIT s
    ```

2.  Calculate the average download size in KB and the average processing time in milliseconds, grouped by user ID:

    ```
    aggregate-stats :download_size :processing_time avg_download_kb avg_processing_ms GROUP BY user_id AGGREGATE_TYPE AVERAGE OUTPUT_SIZE_UNIT KB OUTPUT_TIME_UNIT ms
    ```

## 2. Deliverables

The following files have been committed to the GitHub repository:

* **Modified Directives.g4:** `wrangler-core/src/main/antlr4/io/cdap/wrangler/parser/Directives.g4`
* **New Java Source Files:**
    * `wrangler-api/src/main/java/io/cdap/wrangler/api/parser/ByteSize.java`
    * `wrangler-api/src/main/java/io/cdap/wrangler/api/parser/TimeDuration.java`
* **Modified Java Source Files:**
    * `wrangler-api/src/main/java/io/cdap/wrangler/api/parser/Token.java`
    * `wrangler-api/src/main/java/io/cdap/wrangler/api/parser/UsageDefinition.java`
    * `wrangler-api/src/main/java/io/cdap/wrangler/api/parser/TokenDefinition.java`
    * `wrangler-core/src/main/java/io/cdap/wrangler/parser/RecipeCompiler.java`
    * `wrangler-core/src/main/java/io/cdap/wrangler/parser/GrammarBasedParser.java`
    * `wrangler-core/src/main/java/io/cdap/wrangler/parser/TokenGroup.java`
    * `wrangler-core/src/main/java/io/cdap/wrangler/directive/AggregateStats.java` (New directive implementation)
* **New and Modified Unit Test Files:**
    * `wrangler-core/src/test/java/io/cdap/wrangler/parser/ByteSizeTest.java` (New)
    * `wrangler-core/src/test/java/io/cdap/wrangler/parser/TimeDurationTest.java` (New)
    * `wrangler-core/src/test/java/io/cdap/wrangler/parser/GrammarBasedParserTest.java` (Modified to include new syntax tests)
    * `wrangler-core/src/test/java/io/cdap/wrangler/directive/AggregateStatsTest.java` (New)
* **Evidence of successful build and test execution:** (Maven build logs and test execution reports will be available in the commit history).
* **prompts.txt:** (If AI tooling was used, the prompts will be recorded in this file).
