Loan Calculator
==============

Web application to calculate the amortisation plan of a loan/mortgage.
The application is developed in Java 8 and makes use of the framework Vaadin 7.5 to create the UI.

Repayment plan
--------------

The repayment plan or amortisation plan is comprised of repayments spread over time. The number of repayments and their value can be calculated according to different systems (capital and interest, interest only, etc.). For a French Amortization System with capital and interest (see [http://www.gyplan.com/amofran_en.html](http://www.gyplan.com/amofran_en.html) for further details), the repayment plan is characterised by these attributes:
  
* loan amount: borrowed capital that must be repaid
* annual interest rate: the interest rate must be adapted to the number of periods of the loan. For instance, if the repayment plan is scheduled on a monthly basis, 
the interest rate to use to calculate the periodic payments is 1/12 of the annual interest rate
* number of annual payments: number of repayments in a year
* loan length: number of repayments needed to pay off the principal of the loan
* periodic payment: value of the periodic amortization
  
Periodic payment and loan length are mutually exclusive: only one can be fixed and the other is calculated automatically.
  
In addition to the periodic payments, extra overpayments can be made during the life of the loan.
Every time an overpayment is made, the principal decreases and thus the amortization plan must be recalculated. When recalculating the amortization plan, the borrower can choose to fix the length of the loan or the periodic amortization.
Consequently in order to recalculate the amortization plan after an overpayment, it is necessary to specify the capital paid off (overpayment amount), the payment period when the overpayment is made and the type of overpayment (to keep the length of the loan or to keep the periodic amortisation). 


Repayment
---------

A loan repayment is defined by the following attributes:
* start balance: principal of the loan left before the repayment
* payment: amount of money paid in this repayment
* capital paid off: amount of the payment employed to pay off the principal balance of the loan
* interest paid: amount of the payment employed to pay interests. Thus payment = capitalPaidOff + interestPaid
* end balance: capital to be repaid after the repayment. Thus endBalance = startBalance - capitalPaidOff
* cumulative capital paid off: total capital paid off so far including this repayment
* cumulative interest: total interest paid so far including this repayment
* total cost to date: sum of all the repayments up to date. Thus totalCostToDate = cumulativeCapitalPaidOff + cumulativeInterest

When the repayment is considered as part of a repayment plan, two more attributes are needed:
* payment number: the number of this repayment in the list of repayments (1st, 2nd, 3rd and so on)
* period number: the number of the period (the loan repayment plan is divided into several periods) in which this repayment happens. 

In general, payment number and period number are the same as only a payment happens during a period. Only when there is an overpayment (extra capital is paid off), 
there are two different payments in the same period.


How to run the application
========

To compile the entire project, run "mvn install".
To run the application, run "mvn jetty:run" and open http://localhost:8080/ .

To develop the theme, simply update the relevant theme files and reload the application.
Pre-compiling a theme eliminates automatic theme updates at runtime - see below for more information.

Debugging client side code
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean vaadin:compile-theme package"
  - See below for more information. Running "mvn clean" removes the pre-compiled theme.
- test with "mvn jetty:run-war

Using a precompiled theme
-------------------------

When developing the application, Vaadin can compile the theme on the fly when needed,
or the theme can be precompiled to speed up page loads.

To precompile the theme run "mvn vaadin:compile-theme". Note, though, that once
the theme has been precompiled, any theme changes will not be visible until the
next theme compilation or running the "mvn clean" target.

When developing the theme, running the application in the "run" mode (rather than
in "debug") in the IDE can speed up consecutive on-the-fly theme compilations
significantly
