CSE 373 16su, 7/20/2016
Benjamin Jin
TA: Dan Butler
HW #3: TextAssociator

Favorite Sentence: greeting New World I myself is mirth in scrive railroad telegraphy and bear a 
child howler added to compilation structures

MyClient comment: MyClient provides words that rhyme with each other to help the user write 
raps and poetry. The program prompts the user for a text file of rhyming words and a word to rhyme.
It then outputs a list of rhyming words to the console or the word "none" if there are no words 
that rhyme with the inputted word. It uses the TextAssociator to create associations between words 
that rhyme, assuming the provided text file is of standard format.

1)	For Decision #1, I chose to start with a table size of 23 since it was the first prime number 
greater than 20 (I knew I would have to use at least 20 for MyClient.java so I just decided to 
have 23 be the smallest size). For Decision #2, I wanted to expand when the load factor was 0.75 
so that it would resize when it wasn't too close to full and wouldn't resize unnecessarily 
early (if I resized at a smaller load factor it would create a lot of space quickly). For 
Decision #3, I decided to resize to the next prime number that was greater than double the current 
number of buckets. I chose to do this because it would be close to doubling in size and because it 
would make it easy to switch to Quadratic Probing if I ever wanted to do so.

2)	I used String's hashcode method because it was most likely more optimized than I was capable 
of doing. It seemed to work fine for the assignment. I also considered Quadratic Probing as a 
collision solution, but decided to just stick with the hash function that was already implemented.

3)	If I were to implement a different collision resolution scheme I would probably use Quadratic 
Probing because if I had a prime number of buckets and kept my load factor under 0.5 I would be 
guaranteed to find a bucket in TableSize/2 probes. This would mean having to change when I resize 
to make sure my load factor was always under 0.5 instead of 0.75. Since I'm already using prime 
table sizes I would not have to make any changes in that aspect.

4)	I spent about 2 days working on this assignment. The most challenging aspect of the assignment 
was figuring out the resize relationship (when to resize and what to resize to). Other than that 
the assignment wasn't too difficult once I understood how each object worked with the others.