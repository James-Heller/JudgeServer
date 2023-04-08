package pers.jamestang.judgeServer.runner

import org.junit.jupiter.api.Test
import pers.jamestang.judgeServer.entity.LanguageType
import pers.jamestang.judgeServer.judge.Judges
import kotlin.test.assertEquals

class JudgesTest {

    @Test
    fun judgesJavaTest(){

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

        assertEquals(result[0].md5Check, true)
        assertEquals(result[1].md5Check, true)
    }

    @Test
    fun judgesCTest(){

        val result = Judges(
            LanguageType.C,
            """
                #include<stdio.h>
                int main(){
                    int a,b,c;
                    scanf("%d%d", &a, &b);
                    c = a + b;
                    printf("%d", c);
                    return 0;
                }
            """.trimIndent(),
            "add"
        ).judge()

        println(result)

        Judges.clearBinaryDir()

        assertEquals(result[0].md5Check, true)
        assertEquals(result[1].md5Check, true)
    }

    @Test
    fun judgesGoTest(){

        val result = Judges(
            LanguageType.Go,
            """
                package main
                
                import "fmt"
                func main(){
                    var a int
                    var b int
                    fmt.Scanf("%d%d", &a, &b)
                    fmt.Print(a+b)
                }
            """.trimIndent(),
            "add"
        ).judge()

        println(result)
        Judges.clearBinaryDir()

        assertEquals(result[0].md5Check, true)
        assertEquals(result[1].md5Check, true)
    }

    @Test
    fun judgesPyTest(){

        val result = Judges(
            LanguageType.Python3,
            """
                str = input()
                arr = str.split(' ')
                result = int(arr[0]) + int(arr[1])
                print(result, end='')
            """.trimIndent(),
            "add"
        ).judge()

        println(result)
        Judges.clearBinaryDir()

        assertEquals(result[0].md5Check, true)
        assertEquals(result[1].md5Check, true)
    }
}