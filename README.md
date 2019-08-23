<H1>SUDOKU VOCABULARY PRACTICE APP</H1>


Icon Credits:

* *Microphone free icon* made by [Smashicons](https://smashicons.com/) from [www.flaticon.com](https://www.flaticon.com/home)
* *Checked free icon* made by [Eleonor Wang](https://www.flaticon.com/authors/eleonor-wang) from [www.flaticon.com](https://www.flaticon.com/home)
* *Speaker free icon* made by [Gregor Cresnar](https://www.flaticon.com/authors/gregor-cresnar) from [www.flaticon.com](https://www.flaticon.com/home)



We have implemented the following user stories and TDD examples in our first iteration:

1.
User story: As a user, I want to be presented with a main menu screen so that I can select between 2 different modes. <br/>
Tdd example: When the app is opened, the main menu screen is open with two buttons mode A and mode B which will each generate a puzzle when pressed. <br/>
Refined tdd example:<br/>
Given: that the app is installed <br/>
When: the app is opened <br/>
Then: the main menu screen will appear with two buttons mode A and mode B <br/>

Status: Complete ✅  <<br/>

2.
User story: As a user, I want the word options listed on the screen so that I know which words I need to put into the cells to complete the game. <br/>
Tdd example: when playing the game, there is a word bank at the bottom with 9 different words.<br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/>
When: the user is filling in the grid <br/>
Then: there is a word bank on the screen with 9 different words <br/>

Status: Complete ✅  <br/>

3.
User Story: As a user, I want to be able to tap the cell and then tap the word so that I can place the word into that cell. <br/>
Tdd example: when playing the game, pressing on the cell will highlight it, and then tapping on the word will place that word into the cell. <br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/>
When: the user is filling in the grid <br/>
Then: pressing on a cell will highlight it and then pressing the word will place that word in the cell <br/>

Status: Complete ✅ <br/>

4.
User story: As a user, I want to be able to delete the word from the cell so that the cell is empty on the board. <br/>
Tdd example: when playing the game, pressing on the cell will highlight it, and then pressing the delete button will remove the word from that cell. <br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/>
When: the user is filling in the grid <br/>
Then: pressing on a cell that was filled in will highlight it and then pressing the delete button will remove that word from the cell <br/>

Status: Complete ✅   <br/>

5.
User Story: As a user, I want feedback on my complete solution so that I can know if it was wrong or right <br/>
Tdd example: when the puzzle is complete, there will be a widget/toast at the end saying if the user is right or wrong <br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/>
When: the user has finished filling in the grid  <br/>
Then: a message will appear informing the user if their grid is correct or incorrect <br/>

Status: Complete ✅   <br/>

6.
User Story: As a user, I want to be able to peek at the correct translation of a word, so that I can try to remember it in filling out the puzzle. <br/>
Tdd example: When a user selects a Sudoku cell that is part of the pre-filled configuration of a puzzle, the translation of that word is momentarily displayed through a toast. <br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/>
When: the user presses a pre-filled cell <br/>
Then: the translation of that word is momentarily displayed through a toast <br/>

Status: Complete ✅  <br/>

7.
User Story: As a user, I want the time on the screen so that I can see how long I am taking <br/>
Tdd example: when the puzzle is generated, a stopwatch appears on the screen and begins counting upwards from 0. <br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/> 
When: the puzzle is generated <br/>
Then: a stopwatch appears on the screen and begins counting upwards from 0 <br/>
Status: Complete ✅    <br/>



<B>Iteration 2:
We have implemented the following user stories and TDD examples in our second iteration: <B/> <br/>

8.
User Story: As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts. <br/>
Tdd example: when the user plays the game on the tablet, the words are displayed larger and are fit to the tablet screen. <br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/>
When: the user plays the game on the tablet <br/>
Then: the words are displayed larger and are fit to the screen <br/>

Status: Complete ✅  <br/>
Note: User can also change the size of font in system settings on their device <br/>

9.
User Story: As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font that standard mode. <br/>
Tdd example: when the user rotates their device, the game is saved and playable in landscape mode  <br/>
Refined tdd example: <br/>
Given: that any game mode is enabled <br/>
When: the user rotates their device <br/>
Then: the game is saved and playable in landscape mode <br/>

Status: Complete ✅    <br/>
Note: User can also change the size of font in system settings on their device<br/>

10.
User Story: As a teacher, I want to specify a list of word pairs for my students to practice this week. <br/>
Tdd example: when the teacher presses the add words as teacher button, they can add a CSV file of English and Japanese word pairs to the database <br/>
Refined tdd example: <br/>
Given: that the user is at the add your own words menu screen <br/>
When: the user presses the add words from your teacher button <br/>
Then: the user can add a CSV file of English and Japanese words to the database <br/>

Status: Complete ✅   <br/>
Note: The CSV structure is an English word followed by a Japanese word<br/>

11.
User Story: As a student working with a textbook, I want to load pairs of words to practice from each chapter of the book.</br/>
Tdd example: when the student presses the add words by other sources button, they can add a CSV file of English and Japanese word pairs to the database <br/>
Refined tdd example:<br/>
Given: that the user is at the add your own words menu screen <br/>
When: the user presses the add words from other sources button <br/>
Then: the user can add a CSV file of English and Japanese words to the database <br/>

Status: Complete ✅   <br/>
Note: The CSV structure is an English word followed by a Japanese word <br/>

12.
User Story: As a student, I want the Sudoku app to keep track of the vocabulary words that I am having difficulty recognizing so that they will be used more often in my practice puzzles. <br/>
Tdd example: When the user adds an answer, if the word is correct the corresponding difficulty level will decrease by one, otherwise it will increase by one. After the user stops or finishes the game, the difficulty levels will be updated in database <br/>
Refined tdd example:<br/>
Given: that any game mode is enabled,and the user is filling in the grid. <br/>
When: the user enters a word into a cell <br/>
Then: the corresponding difficulty level will decrease by one if the word is correct, otherwise it will increase by one. After the user stops or finishes the game, the difficulty levels will be updated in the database <br/>

Status: Complete ✅   <br/>

13.
User Story: As a student who wants to practice my understanding of spoken words in the language that I am learning, I want a listening comprehension mode. In this mode, numbers will appear in the prefilled cells and the corresponding word in the language that I am learning will be read out to me when I press the number. <br/>
Tdd example: when the user presses the listening mode switch, the words in the prefilled cells are now shown as numbers on the board, and when those words are pressed, the corresponding word will be read out. <br/>
Refined tdd examples: <br/>
Scenario 1 <br/>
Given: the listening comprehension mode switch is enabled<br/>
When: the puzzle is generated <br/>
Then: the user sees a standard Sudoku grid with some pre-filled cells showing digits in the range 1..9 and all other cells empty <br/>

Scenario 2 <br/>
Given: that the user is filling in the grid in listening comprehension mode,and that the grid includes a cell with the prefilled digit 1 and that word pair 1 is (thank you, ありがとう) <br/>
When: the user presses the prefilled cell having the digit 1 <br/>
Then: the user hears the word "ありがとう" read out and pronounced in Japanese <br/>

Scenario 3 <br/>
Given: that the user is filling in the grid in listening comprehension mode,and that the grid includes a cell with the prefilled digit 1 and that word pair 1 is (thank you, ありがとう) <br/>
When: the user selects a non-prefilled cell to enter the word "thank you" <br/>
Then: the word "thank you" appears in the list of words that may be selected, but not in the first position <br/>

Scenario 4 <br/>
Given: that the user is filling in the grid in listening comprehension mode<br/>
When: the users presses a cell and hears the word "ありがとう" <br/>
Then: the user does not see the word "ありがとう" anywhere on the game grid <br/>

Status: Complete ✅    <br/>

<B>Iteration 3:
We have implemented the following user stories and TDD examples in our third iteration: <B/><br/>

14.
User Story: As a teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. <br/>
Tdd example: When the user enters the 6x6 grid version, the overall grid will be  divided into rectangles of six cells each (2x3). <br/>
Refined tdd examples: <br/>

Scenario 1 <br/>
Given: that the 4x4 grid mode is enabled <br/>
When: the puzzle is generated <br/>
Then: the overall grid will be divided into squares of 4 cells each <br/>

Scenario 2 <br/>
Given: that the 6x6 grid mode is enabled <br/>
When: the puzzle is generated <br/>
Then: the overall grid will be divided into rectangles of six cells each (2x3) <br/>

Status: Complete ✅   <br/>

15.
User Story: As a vocabulary learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. <br/>
Tdd example: When the user enters the 12x12 mode, the overall grid will be divided into rectangles of 12 cells each (3x4). <br/>
Refined tdd example: <br/>
Given: that the 12x12 grid mode is enabled, and the user is playing on a tablet. <br/>
When: the puzzle is generated <br/>
Then: the overall grid will be divided into rectangles of 12 cells each (3x4) <br/>

Status: Complete ✅    <br/>

16.
User Story: As a vocabulary learner, I want a difficult mode so that I can practice on the words that I am struggling with <br/>
Tdd example: When the user selects the difficult game mode, 9 random words selected from the top 100 most difficult words in the database will be used in the game. <br/>
Refined tdd example: <br/>
Given: that the user is at the game menu screen <br/>
When: the user presses the difficult game button <br/>
Then: 9 random words will be selected from the top 100 most difficult words in the database and appear as the list of words for that game. <br/>

Status: Complete ✅   <br/>

17.
User Story: As a teacher, I want a teacher mode so that students can practice with words that I have given them. <br/>
Tdd example: When the user selects the teacher mode, 9 random words from the given teacher words will be used in the game. <br/>
Refined tdd example: <br/>
Given: that the user is at the game menu screen, and the user has entered more than 9 words from your teacher. <br/>
When: the user presses the teacher game button <br/>
Then: 9 random words will be selected from the words entered from your teacher in the database and appear as the list of words for that game. <br/>

Status: Complete ✅   <br/>

18.
User Story: As a user, I want to be given immediate feedback on the cells I fill in so that I know whether I am right or wrong. <br/>
Tdd example: When the user fills in a cell, the cell colour will change if the answer is incorrect <br/>
Refined tdd examples: <br/>
Scenario 1 <br/>
Given: that any game mode is enabled <br/>
When: the user enters a word into a cell <br/>
Then: the cell colour will be red if the word is incorrect and the message “Sorry, but you made a mistake :(“ will appear  <br/>
Scenario 2 <br/>
Given: that any game mode is enabled <br/>
When: the user enters a word into a cell <br/>
Then: the cell colour will not change if the word entered is correct <br/>

Status: Complete ✅   <br/>


<B>Iteration 4:
Follwing user stories are implented for the iteration 4: <B/> <br/>

19.

User Story: As a user, I want a flashcard game so that I can practice my vocabulary using flashcards before testing my skills in the game<br/>
Tdd example: When the user selects the flash card game, there will be a list of words from the database to select from. After selecting the words they want to practice, the user is presented with flashcards, and when the user taps the word on a flashcard, the flashcard will flip to show the corresponding translation of the word. Also, the user can press the sound button to listen to the word, and the microphone button to practice their pronunciation. <br/>
Refined tdd examples: <br/>

Scenario 1 <br/>
Given: that the user is at the main menu screen <br/>
When: the user presses the flashcards button<br/>
Then: a scrollable list of all the words in the database, sorted from lowest to highest score/difficulty rating, will appear for the user to choose from and create a set of flashcards of 1 or more words<br/>

Scenario 2 <br/>
Given: that the user is at the screen with the list of all words<br/>
When: the user presses a row<br/>
Then: a checkmark will appear to indicate that the word has been selected for practice, and the clear and done buttons will be enabled<br/>

Scenario 3<br/>
Given: that the user has selected 1 or more rows in the list<br/>
When: the user presses the clear button<br/>
Then: all selected rows will be deselected and the clear and done buttons will be disabled<br/>

Scenario 4<br/>
Given: that the user has selected 1 or more rows in the list<br/>
When: the user presses the done button<br/>
Then: A flashcard will appear with a word the user has selected from the list of words<br/>

Scenario 5<br/>
Given: that the user is playing with the flashcards<br/>
And the flashcard set contains 2 or more words<br/>
And the user has selected a flashcard<br/>
When: the user swipes horizontally across the screen<br/>
Then: another flashcard will appear from the flashcard set<br/>

Scenario 6<br/>
Given: that the user is playing with the flashcards<br/>
When: the user presses the play sudoku button<br/>
Then: the language menu screen will appear for the user to then start playing a sudoku game using the flashcard words<br/>

Scenario 7<br/>
Given: that the user is playing with the flashcards<br/>
When: the user presses the play sudoku button<br/>
Then: random words from the flashcard set will be used in the sudoku game if the number of flashcard words is greater than the chosen grid size. Otherwise, words from the flashcard set combined with random words from the database will be used if the number of flashcard words is less than the chosen grid size.<br/>

Scenario 8<br/>
Given: that the user is playing with the flashcards<br/>
And the user has selected a flashcard<br/>
When: the user presses on the flashcard word<br/>
Then: the card will flip to show the corresponding translation of the word<br/>

Scenario 9<br/>
Given: that the user is playing with the flashcards<br/>
And the user has selected a flashcard<br/>
When: the user presses on the sound icon on the flashcard<br/>
Then: the word on the flashcard will be read out <br/>

Scenario 10<br/>

Given: that the user is playing with the flashcards<br/>
And the user has selected a flashcard<br/>
And the user presses on the microphone icon on the flashcard<br/>
And the user has selected “日本語 (日本)” as their primary voice input in the device’s system settings<br/>
When: the user speaks into the device<br/>
Then: if the user is correct, the message “Sounds right!” will appear. Otherwise, the message “That didn’t sound quite right…” will appear<br/>
NOTE: Pronunciation only works on Android devices, not the emulator, and the device must have a working internet connection.<br/>
To practice Japanese pronunciation, the user must select “日本語 (日本)” as their primary voice input. This can be done through the devices Settings app [Settings -> Language & Input -> Google Voice Typing -> Languages -> Tap and hold “日本語 (日本)”]<br/>
Status: Complete ✅ <br/>


20.
<br/>
User Story: As a user, I want a pronunciation mode so that I can practice my speaking skills.<br/>

Tdd example: when the user presses on the cell, they have to speak the words that they want to enter<br/>
Refined tdd example:<br/>

Scenario 1<br/>
Given: that speech recognition mode is enabled<br/>
When: the user is filling in the grid<br/>
Then: the user will press the cell and then speak the word they want to be entered into that cell<br/>

Scenario 2<br/>
Given: that speech recognition mode is enabled<br/>
When: the user is filling in the grid<br/>
Then: the list of words are at the bottom of the screen but the user cannot tap them to enter them into cells<br/>
NOTE: Pronunciation mode only works on Android devices, not the emulator, and the device must have a working internet connection<br/>
Status: Complete ✅<br/>

21.
Use Story: As a user who wants a challenging mode, I want different difficulty levels where the number of pre filled cells varies.<br/>
Tdd example: when the user chooses the easy level, there are more pre filled cells than the medium level, and the medium level has more pre filled cells than the difficult level, and the difficult level has the least number of pre filled cells.<br/>

Refined tdd example:<br/>

Scenario 1<br/>
Given: that the user is at the main menu screen<br/>
When: the user presses easy level<br/>
Then: there are more pre-filled cells in the game than the medium and difficult levels<br/>

Scenario 2<br/>
Given: that the user is at the main menu screen<br/>
When: the user presses medium level<br/>
Then: there are more pre-filled cells in the game than the difficult level but fewer pre-filled cells than the easy level<br/>

Scenario 3<br/>
Given: that the user is at the main menu screen<br/>
When: the user presses difficult level<br/>
Then: there are fewer pre-filled cells in the game than the easy and medium levels<br/>
Status: Complete ✅<br/>

22.
User Story: As a user, I want the app to be more visually appealing so that I am more excited to practice my vocabulary <br/>
Refined tdd example:<br/>
Given: that the app is installed<br/>
When: the user opens the app<br/>
Then: there are colours and images to make the app more visually appealing<br/>
Status: Complete ✅<br/>

23.
User Story: As a user, I want the app to have a button where all my incorrect answers will be cleared from the board <br/>
Refined tdd example:<br/>
Given: that the user is filling in the grid<br/>
When: the user presses the clear button<br/>
Then: all the incorrect answers were be deleted from the cells<br/>
Status: Complete ✅<br/>

24.
User Story: As a user, I want the app to be able to immediately replace a word in a cell without deleting the old word first<br/>
Refined tdd example:<br/>
Given: that the user is filling in the grid<br/>
And the user has selected a cell that they previously filled<br/>
When: the user presses on another word<br/>
Then: that word is immediately inserted into the cell, replacing the previous word that was in the cell<br/>
Status: Complete ✅<br/>



 
 
