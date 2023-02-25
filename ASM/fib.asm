# Author: James West
# Course: CSC310 Computer Architecture and Operating Systems
# Assignment: 11
# Description: CLI which greets user, asks math question, checks response, and reports results

# Data of program
.data
# String Data
	start_msg: .asciiz "Hello, welcome to MathQuiz, here is your first problem:\n"
	end_msg_p1: .asciiz "You solved "
	end_msg_p2: .asciiz " math problems and got "
	end_msg_p3: .asciiz " correct and "
	end_msg_p4: .asciiz " incorrect, for a score of "
	end_msg_p5: .asciiz "%.\nThanks for playing!\n(exit)"
	q_start: .asciiz "What is "
	q_end: .asciiz "? "
	correct: .asciiz "Correct!\n"
	incorrect: .asciiz "Incorrect!\n"
	addition:.asciiz " + "
	subtraction: .asciiz " - "
	division: .asciiz " / "
	multiplication: .asciiz " * "
	modulo: .asciiz " % "
	
# Numerical data
	answer: .word 0
	user_answer: .word 0
	operand1: .word 0
	operand2: .word 0
	operator: .word 0
	result: .word 0
	count: .word 0
	correct_count: .word 0
	percent: .float 100.0

.text
	.globl main # entry

main:
	jal greet_user
	j loop_questions

# Main loop
loop_questions:
	jal get_operators # get random operators
	jal ask_question # print question to console and store user answer in user_answer
	jal exit_if # exit if user_answer is -1
	jal compare_answer# check if answer correct, increment count, if correct increment correct count, display result to user
	j loop_questions # loop until exit_if condition is met


greet_user:
	# print start_msg
	li $v0, 4
	la $a0, start_msg 
	syscall
	
	jr $ra # jump back

get_operators:
	# Generate random number
	li $v0, 42
	li $a1, 20 # upper limit
	syscall
	
	addiu $a0, $a0, 1 # add 1
	sw $a0, operand1 # store in variable
	
	# Repeat above for operand2
	li $v0, 42
	li $a1, 20
	syscall
	
	addiu $a0, $a0, 1
	sw $a0, operand2
	
	# Repeat above for operator
	li $v0, 42
	li $a1, 5 # upper limit
	syscall
	sw $a0, operator
	
	jr $ra # jump back

ask_question:
	# print q_start
	li $v0, 4
	la $a0, q_start
	syscall
	
	# print operand1
	li $v0, 1
	lw $a0, operand1
	syscall
	
	# store return address to sp
	move $sp, $ra
	jal switch_operator
	
	# print operand2
	li $v0, 1
	lw $a0, operand2
	syscall
	
	# print q_end
	li $v0, 4
	la $a0, q_end
	syscall
	
	# get user input and store in user_answer
	li $v0, 5
	syscall
	sw $v0, user_answer

	jr $sp # jump back

switch_operator:
	# Jump to label based on operator value
	lw $t1, operator
	beq $t1, 0, sum
	beq $t1, 1, difference
	beq $t1, 2, product
	beq $t1, 3, quotient
	beq $t1, 4, mod	

sum:
	# print " + "
	li $v0, 4
	la $a0, addition
	syscall
	
	# load and sum operands
	lw $t1, operand1
	lw $t2, operand2
	add $t1, $t1, $t2

	sw $t1, answer # store sum as answer
	
	jr $ra # jump back
	
difference:
	# print " - "
	li $v0, 4
	la $a0, subtraction
	syscall
	
	# load and subtract operands
	lw $t1, operand1
	lw $t2, operand2
	sub $t1, $t1, $t2
	
	sw $t1, answer # store difference as current answer
	
	jr $ra
	
quotient:
	# print " / "
	li $v0, 4
	la $a0, division
	syscall
	
	# load and divide (integer division) operands
	lw $t1, operand1
	lw $t2, operand2
	div $t1, $t1, $t2
	
	sw $t1, answer # store quotient as current answer
	
	jr $ra
	
product:
	# print " * "
	li $v0, 4
	la $a0, multiplication
	syscall
	
	# load operands and multiply
	lw $t1, operand1
	lw $t2, operand2
	mul $t1, $t1, $t2
	
	sw $t1, answer # store product as current answer
	
	jr $ra
	
mod:
	# print operator string
	li $v0, 4
	la $a0, modulo
	syscall
	
	# store operands and perform modulo operation
	lw $t1, operand1
	lw $t2, operand2
	div $t1, $t2
	mfhi $t1
	
	sw $t1, answer # store mod as current answer
	
	jr $ra

compare_answer:
	# load answer and user answer
	lw $t1, answer
	lw $t2, user_answer
	
	# increment question count by 1
	lw $t3, count
	addu $t3, $t3, 1
	sw $t3, count
	
	# if answer = user answer jump to on_correct
	beq $t1, $t2, on_correct
	# else jump to on_incorrect
	j on_incorrect
	
on_correct:
	# print correct
	li $v0, 4
	la $a0, correct
	syscall
	
	# increment correct count by 1
	lw $t1, correct_count
	addu $t1, $t1, 1
	sw $t1, correct_count
	
	jr $ra # return

on_incorrect:
	# print incorrect
	li $v0, 4
	la $a0, incorrect
	syscall
	
	jr $ra # return

exit_if:
	# load user answer
	lw $t1, user_answer
	# if user answer is -1 exit
	beq $t1, -1, exit
	# else return
	jr $ra
exit:
	# print end msg part 1
	li $v0, 4
	la $a0, end_msg_p1
	syscall
	
	# print count
	li $v0, 1
	lw $a0, count
	syscall
	
	# print end msg part 2
	li $v0, 4
	la $a0, end_msg_p2
	syscall
	
	# print correct count
	li $v0, 1
	lw $a0, correct_count
	syscall
	
	# print end msg part 3
	li $v0, 4
	la $a0, end_msg_p3
	syscall
	
	# load correct count and count and get difference
	lw $t1 correct_count
	lw $t2 count
	sub $t3, $t2, $t1
	
	# print above difference as incorrect count
	li $v0, 1
	move $a0, $t3
	syscall
	
	# print end msg part 4
	li $v0, 4
	la $a0, end_msg_p4
	syscall
	
 	mtc1 $t1, $f1 # load correct count to floating processor
 	mtc1 $t2, $f2 # load count to floating processor
 	div.s $f1, $f1, $f2 # divide count by correct count
 	l.s $f2, percent # load 100
 	mul.s $f1, $f1, $f2 # multiply by 100

	# print percentage of correct answers
	li $v0, 2 
	mov.s $f12, $f1
	syscall
	
	# print end message part 5
	li $v0, 4
	la $a0, end_msg_p5
	syscall
	
	li $v0, 10
	syscall
