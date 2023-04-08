package pers.jamestang.judgeServer.runner

import org.junit.jupiter.api.Test
import pers.jamestang.judgeServer.compiler.Compile
import pers.jamestang.judgeServer.entity.LanguageType

class CompileTest {


    @Test
    fun testCompileC(){

        val result = Compile(
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
        ).compile()

        println(result)
    }

    @Test
    fun testCompileJava(){

        val result = Compile(
            LanguageType.Java,
            """
                import java.util.Scanner;
                public class add{
                    public static void main(String args[]){
                        int a,b;
                        Scanner scanner = new Scanner(System.in);
                        a = scanner.nextInt();
                        b = scanner.nextInt();
                        System.out.print(a+b);
                        scanner.close();
                    }
                }
                        
            """.trimIndent(),
            "add"
        ).compile()

        println(result)
    }

    @Test
    fun testCompilePython(){

        val result = Compile(
            LanguageType.Python3,
            """
                str = input()
                arr = str.split(' ')
                print(int(arr[0]) + int(arr[1]), end='')
            """.trimIndent(),
            "add"
        ).compile()

        println(result)
    }

    @Test
    fun testCompileGo(){

        val result = Compile(
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
        ).compile()


        println(result)
    }
}