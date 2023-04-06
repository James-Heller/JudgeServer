package pers.jamestang.judgeServer.runner

import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import pers.jamestang.judgeServer.config.Config
import pers.jamestang.judgeServer.entity.Result
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Random

class Runner(private val exePath: String, private val testCaseDir: String, private val ruleName: String?) {


    fun getResult(cpuTime: Int, realTime: Int, maxMemory: Int, args: List<String>?,
                  env: List<String>?, processNumber: Int, maxOutputSize: Int, maxStack: Int): List<Result>{

        val rawResult = this.run(cpuTime, realTime, maxMemory, args, env, processNumber, maxOutputSize, maxStack)

        val results = mutableListOf<Result>()

        rawResult.forEach {
            it as JSONObject
            results.add(Result(it["cpu_time"] as Int, it["real_time"] as Int, it["memory"] as Int, it["md5check"] as Boolean))
        }

        return results
    }

    fun run(
        cpuTime: Int, realTime: Int, maxMemory: Int, args: List<String>?,
        env: List<String>?, processNumber: Int, maxOutputSize: Int, maxStack: Int
    ): JSONArray {

        val testCases = getTestCase(testCaseDir)
        val resultSet = JSONArray()


        testCases.forEach {

            it as JSONObject
            val inputFilePath = "${Config.testCaseBasePath}/$testCaseDir/${it["in"] as String}"
            val resultFilePath = "${Config.testCaseBasePath}/$testCaseDir/${it["out"] as String}"
            val executeResultFilePath = "${Config.executeResultBasePath}/" + Random().nextInt().toString() + "output.out"
            val cmd = configWrapper(
                cpuTime,
                realTime,
                maxMemory,
                processNumber,
                maxOutputSize,
                maxStack,
                this.exePath,
                inputFilePath,
                executeResultFilePath,
                executeResultFilePath,
                args,
                env,
                ruleName

            )

            val builder = ProcessBuilder()
            builder.command(cmd)
            val process = builder.start()

            val result = JSONObject.parse(readProcessOutput(process))

            val outMD5 = getMD5(File(executeResultFilePath).readText())
            val resultMD5 = getMD5(File(resultFilePath).readText())
            result["md5check"] = md5Check(outMD5, resultMD5)
            resultSet.add(result)

            //Delete output file when got result
            File(executeResultFilePath).delete()
        }


        return resultSet
    }

    private fun configWrapper(
        cpuTime: Int,
        realTime: Int,
        maxMemory: Int,
        processNumber: Int,
        maxOutputSize: Int,
        maxStack: Int,
        exePath: String,
        inputPath: String,
        outputPath: String,
        errPath: String,
        args: List<String>?,
        env: List<String>?,
        ruleName: String?,


    ): MutableList<String>{

        val cmd = mutableListOf<String>()
        cmd.add("./libjudger.so")
        cmd.add("--max_cpu_time=$cpuTime")
        cmd.add("--max_real_time=$realTime")
        cmd.add("--max_memory=$maxMemory")
        cmd.add("--max_process_number=$processNumber")
        cmd.add("--max_output_size=$maxOutputSize")
        cmd.add("--max_stack=$maxStack")
        cmd.add("--exe_path=$exePath")
        cmd.add("--input_path=$inputPath")
        cmd.add("--output_path=$outputPath")
        cmd.add("--error_path=$errPath")
        cmd.add("--log_path=${Config.logBasePath}/judge.log")
        if (ruleName != null) cmd.add("--seccomp_rule_name=$ruleName")
        cmd.add("--uid=0")
        cmd.add("--gid=0")
        args?.forEach {
            cmd.add("--args=$it")
        }

        env?.forEach{
            cmd.add("--env=$it")
        }

        return cmd
    }

    private fun readProcessOutput( process: Process): String{

        return read(process.inputStream) + read(process.errorStream)

    }

    private fun read(inputStream: InputStream): String{
        val sb = StringBuilder()
        try {
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {

                sb.append(line+ '\n')
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }

    private fun getTestCase(testCaseDir: String): JSONArray {

        val file = File("./testcase/$testCaseDir/info.json")

        val jsonInfo = JSONObject.parse(file.readText())

        return jsonInfo["testcases"] as JSONArray
    }

    private fun getMD5(text: String): String{

        val md = MessageDigest.getInstance("MD5")
        md.update(text.toByteArray())

        return BigInteger(1, md.digest()).toString(16)
    }

    private fun md5Check(val1: String, val2: String): Boolean{

        return val1 == val2
    }
}
