To run the standalone application: 
```
./run.sh
```

Assumptions:

- Transfers can only occur between two accounts that exist in the system.
- There are overdraft limits. 
- Transfers occur in only one currency.
- There are no daily transfer limits.
- Only one type of account (i.e Current Account)
 

Room for improvement:
 
- Handling concurrency issues (account A can find itself wiring money it does not have)
- Single entry bookkeeping brings with it certain risks (if one account is debited but the other is not). Need a rollback mechanism.


 