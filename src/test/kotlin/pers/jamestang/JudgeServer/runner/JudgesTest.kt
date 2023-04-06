package pers.jamestang.JudgeServer.runner

import org.junit.jupiter.api.Test
import pers.jamestang.judgeServer.entity.LanguageType
import pers.jamestang.judgeServer.judge.Judges

class JudgesTest {

    @Test
    fun judgesTest(){

        val result = Judges(

            LanguageType.Java,
            """
                import java.util.Scanner;
                public class add{
                    public static void main(String args[]){
                        Scanner scanner = new Scanner(System.in);
                        int a,b ;
                        a = scanner.nextInt();
                        b = scanner.nextInt();
                        System.out.print(a+b);
                        
                    }
                    
                }
            """.trimIndent(),
            "add"
        ).judge()

        println(result)

        Judges.clearBinaryDir()
    }
}