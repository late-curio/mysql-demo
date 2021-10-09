import java.time.Duration

//def dir = "/Users/tcrone/temp/mysql/5.7.35-NO_AGENT"
//def dir = "/Users/tcrone/temp/mysql/8.0.26-NO_AGENT"
//def dir = "/Users/tcrone/temp/mysql/MYSQL-8.0.26-AGENT-7.0.3"
def dir = "/Users/tcrone/temp/mysql/MYSQL-5.7.35-AGENT-7.3.0-1633757094808"

BigInteger count = 0
BigInteger sum = 0
String mysqlVersion = null
String agentVersion = null
BigInteger start = -1
BigInteger end = 0;
new File("$dir/report.csv").splitEachLine(",") { fields ->
    if(fields == null || fields.size() < 4) {
        println "Bad data $fields"
    }
    else {
        def s = fields[1]
        if(s.isNumber()) {
            def ts = new BigInteger(fields[0])
            if(start == -1) {
                start = ts
            }
            else {
                end = ts
            }
            def ms = new BigInteger(s)
            count = count.add(BigInteger.ONE)
            sum = sum.add(ms)
            if(count == 1) {
                mysqlVersion = fields[2]
                agentVersion = fields[3]
            }
            if(ms > 999) {
                println ms
            }
        }

    }
}
def average = sum.divide(count)
def elapsedMs = end.subtract(start)
def duration = Duration.ofMillis(elapsedMs.toLong())
println "MySQL version: $mysqlVersion"
println "Agent version: $agentVersion"
println "Count of rows: $count"
println "Elapsed time: $duration"
println "Average time is: $average"
