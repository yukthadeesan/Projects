```
//autograder in JUnit that score a given implementation using written tests. 

err() {
  echo $1
  exit 1
}

dir="file_to_check"
CP=".\
:lib/hamcrest-core-1.3.jar\
:lib/junit-4.13.2.jar"

test ! -z "$1" \
|| err "Please supply a git repsitory"

rm -rf "${dir}"
git clone $1 "${dir}"

if [ -e ${dir}/ListExamples.java ]  
  then 
    echo "File was found"
  else
    err "File NOT found: ListExamples.java"
fi 

cp TestListExamples.java "${dir}"
cp -r lib "${dir}"
cd "${dir}"

javac -cp "${CP}" *.java  2> ../error.txt
if [ $? -eq 0 ] 
  then 
    echo "File Compiled!"
else
  err "Compilation FAILED!"
fi

java -cp "${CP}" org.junit.runner.JUnitCore TestListExamples \
> ../report.txt

echo "Report saved to: report.txt"

total_tests=$(head -n 2 ../report.txt | tail -n 1 | grep -o "\." | wc -l)
number_of_tests_failed=$(head -n 2 ../report.txt | tail -n 1 | grep -o ".E" | wc -l)

echo "$number_of_tests_failed" tests failed.
echo Score: "$(($total_tests - $number_of_tests_failed))" out of $total_tests

exit
```
