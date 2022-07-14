# account-service

### Service manages system customer accounts
-------------------------------------------
**Architecture:** **Onion** architecture in combination with **Domain-Driven Design** patterns

Implemented ideas:
  - onion architecture + ddd;
  - base centralized exceptions handling aproach;
  - unit tests;
  - retry mechanics + partial response returning;
  - openAPI support;
  - docker support;
  - simple GitHub workflow on push;
  - uml diagram with plantuml.

To Do:
  - increase tests coverage (e.i. integration, project structure);
  - add tiny UI;
  - CI/CD (code analyzers, test coverage, vulnerability check, deployment scripts and tools etc.).

End Points:
  - GET: [http://localhost:8091/v1/accounts/details?customer_id={id}](http://localhost:8091/v1/accounts/details?customer_id={id}) - return all accounts details for 'customer_id', including transactions, if 'customer_id' parameter is not provided - return accounts details for all customers;
  - POST: [http://localhost:8091/v1/accounts](http://localhost:8091/v1/accounts) - create new customer account, if 'initialCredit' > 0 also send transaction to **transaction-service**.  
    Body:  
    `{
        "customerId": "771493f4-cf89-4acf-b131-5c527c495f1d",
        "accountType": "CURRENT",
        "initialCredit": 0
    }`. 
      
H2 DB:   
  - There is script which preconfigure customers, there are ids:  
  `9ff67ec7-8e46-42c5-b1f0-4d86484d466b, 771493f4-cf89-4acf-b131-5c527c495f1d, 8258a5f3-b6fa-4785-ad81-0b12bdfc6b05`.  
  
Run Application (from service root):
  - simple run:
    - ./scripts/run.sh
    - ./scripts/stop.sh
  - run in docker:
    - ./scripts/run_d.sh
    - ./scripts/stop_d.sh

Linked services:
 - https://github.com/Al3ski/transaction-service
