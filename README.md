# AnyTestTrainer
The program is designed to pass tests, as well as to learn the correct answers. At the end of the test, all questions with wrong answers are shown, the correct answers are shown in bold.
There is an opportunity to mix up the order of questions and the order of answers.
Tests are loaded into the program in the form of text files, which should be in the same folder with the program.
The test should be located in a file with the .txt extension and have the following appearance:

:Test title
?The text of the question
!the text of the right answer
.the text of the wrong answer

Question can also include jpg image:

?<path to the image> Text of the question
  
There could be any number of options and only one right answer. If question has only one option (right answer), than other options added randomly from another questions with single answers.
