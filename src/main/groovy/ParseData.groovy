import groovy.io.FileType

import java.time.Duration


File baseDir = new File("/Users/tcrone/temp/mysql")
println "Loading data from ${baseDir.getName()}"

List<File> files = []

baseDir.eachFile(FileType.DIRECTORIES) { file ->
    files << file
}

files.sort { File a, File b -> (a.name <=> b.name) }

files.each { file ->
    doEet(baseDir, file.getName())
}

def doEet(baseDir, run) {
    def dir = "/Users/tcrone/temp/mysql/${run}"
    println "```"
    println run

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
            }

        }
    }
    def average = sum.divide(count)
    def elapsedMs = end.subtract(start)
    def duration = Duration.ofMillis(elapsedMs.toLong())

    BigInteger sampleCount = 0
    BigInteger memsum = 0

    new File("$dir/system.csv").splitEachLine(",") { fields ->
        if(fields == null || fields.size() < 6) {
            println "Bad data $fields"
        }
        else {
            def s = fields[3]
            if(s.isNumber()) {
                def ms = new BigInteger(s)
                sampleCount = sampleCount.add(BigInteger.ONE)
                memsum = memsum.add(ms)
            }

        }
    }

    averageMemory = memsum.divide(sampleCount)

    println "MySQL version: $mysqlVersion"
    println "Agent version: $agentVersion"
    println "Count of rows: $count"
    println "Elapsed time: $duration"
    println "Average time is: ${average}ms"
    println "Average memory used is: $averageMemory"
    println "```"

}
