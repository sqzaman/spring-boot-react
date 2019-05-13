## Before you start
Run `npm install` to install all the dependencies needed.

## To run the application
Run `npm start` to start the dev server which will launch the web browser and start the application.

## To run the test
Run `npm test` to run all the tests.

## Evaluation of Your Work
1. Pay attention to this README file. It indicates whether you respect documentation or not. Carefully follow guidelines at the end of the README file.

2. Name your variables, methods carefully. Always remember, you are not only writing code for computer to execute also for other team members to understand.

3. Don't be shy to refactor the original code. Improvements are always welcome.

4. Do your best and submit your work even if you could not fully complete the assignment with an explanation of what you were able to achieve in no more than 2 sentences. 

## Task requirements
Your team has been asked to develop a customizable questionnaire application where the users can add either text questions or multiple select questions with options.

Your co-worker has already written some code to implement some features of text questions, such as adding, deleting and editing.

You are required to perform the following tasks to finish the project.

### Task - Implement multi select questions

#### Sub Task 1 - Add a multi select question 
A new multi select question should be added to the list when the "Add A Multi Select Question" button is clicked.

The multi select questions should have an "Add Option" button to allow users to add options.

Here is a screen shot of a possible result.

![add-multi-select-question](screenshots/add-multi-select-question.png)

#### Sub Task 2 - Save, Edit and Delete multi select questions
Just like for text questions, the users should be able to save, edit and delete multi select questions.

#### Sub Task 3 - Add options for the multi select question
A new option should be added to the question when the "Add Option" button is clicked

The user should be able to save, edit and delete options. 

Here is a screen shot of a possible result.

![add-options](screenshots/add-options.png)

### Bonus Task - Implement reordering questions and options
The users should be able to reorder the questions and options. 

Each question/option should have an "Up" and "Down" button if the question/option is not the first or the last question/option in the list. Please see the screen shot below.

The first question/option in the list should have a "Down" button and the last question/option should have an "Up" button only.

**Note** The user should still be able to reorder correctly after deleting a question or an option.

![reoreder](screenshots/reorder.png)

## Guidelines 

1. Ideally you should write tests first before implementing any of the features. If you find this is difficult to do, at least have test cases to cover all your implementations.
Make sure your test cases are easy to understand so that they can document your design.

2. In react applications, the state has to be immutable, meaning if you want to change the state, you need to create a new state instead of modifying the state itself.
Take advantage of ES6 object destructuring, spread operator and libraries such as lodash.

3. Try to finish all the tasks first with good test coverage. However, that does **NOT** mean you should copy paste code with similar functions.

    For example, you should **NEVER** have two functions, one for adding a text question and one for adding a multi select question. Try to utilize closure and/or high order functions to generalize your code.

    **Hint:** There might be two kind of similarities that you might be able to generalize. 
    
    One is the state of questions and options. They have almost the same properties with few variations. Your co-worker designed the state to use an array of questions. You are free to structure (normalize/flatten) the state to make your coding easy.
    
    The second is on the UI level. You might notice that the UI for questions and options consists of one input or text and some buttons that function similarly. 

4. Do not add any other dependencies. 

5. Be respectful to the eslint rules. Do **NOT** disable any rules.

5. Make small commits instead of one large commit. **Pay attention to your commit messages, remember you are working in a team environment**. 
   
   Push to the feature branch we created for you with your bitbucket user name. **DO NOT push to the master branch.** 

