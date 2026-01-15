# Projects
All significant and relevant projects to be posted here

1) Bank Accounts (8), Banking system supporting 3 types of accounts, Checking, Saving and CD. I wrote the system to be tested via console input, but for easier testing i implemented a test file to easily test with one run. The system works as follow:
users pick an action, then enter a 6 digit account number and any other valid information. then a transaction ticket is created via the TranTicket class. the ticket is sent to the Bank class where the ticket's information is validated then getAccount() is called which uses a random access file to get the info. the operation is completed in one of the accounts class' subclasses depending on the account type. a general form of exception handling is also implemented for major types of errors both user input or internal operation (insufficient balance etc). In all cases, a receipt gets printed.

2) Wordle, a simple wordle game that uses associative data structures to make the game quick and efficient.
