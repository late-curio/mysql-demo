import java.time.Duration

def run = args[0]
def dir = "/Users/tcrone/temp/mysql/${run}"
println "Loading data from $dir"
println "```"
println run

BigInteger count = 0
BigInteger sum = 0
String mysqlVersion = null
String agentVersion = null
BigInteger start = -1
BigInteger end = 0;
new File("$dir/system.csv").splitEachLine(",") { fields ->
    if(fields == null || fields.size() < 6) {
        println "Bad data $fields"
    }
    else {
        def s = fields[3]
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
                mysqlVersion = fields[4]
                agentVersion = fields[5]
            }
//            if(ms > 999) {
//                println ms
//            }
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
println "Average memory used is: $average"
println "```"
