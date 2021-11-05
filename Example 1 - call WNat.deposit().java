   private void useDepositFunction(String cAddr) {   // cAddr = contract address

        BigInteger amount = new BigInteger("100000000000000000");   // amount to deposit, this 1 SGB in Wei

        // create connection to blockchain RPC
        Web3j web3j = Web3j.build(new HttpService(_rpcUrl)).ethChainId().setId(_chainID);

        // create instance of WNat
        WNat wsgb = WNat.load(_contractAddress, web3j, _credentials, _GAS_PRICE, _GAS_LIMIT);
  
        try {

            // we need to specify "chainID" otherwise we error out with EIP-155
            TransactionManager txManager = new RawTransactionManager(web3j, _credentials, _chainID));

            EthSendTransaction transactionResponse = txManager.sendTransaction(
                    getNetworkGasPrice(web3j),                    // you need to implement
                    new BigInteger("8000000"),                    // block gas limit
                    cAddr,                                        // contract address
                    wsgb.deposit().encodeFunctionCall(),          // encoded ref to contract func
                    amount);                                      // amount of SGB to send to deposit()

            String txHash = transactionResponse.getTransactionHash();    // check that we're good

            myLog("TXHASH1", txHash);   // myLog is custom log function, replace with Log.d() on android

            if (transactionResponse.hasError()) {
                myLog("TXHASH1", transactionResponse.getError().getMessage());
                myLog("TXHASH1", transactionResponse.getError().getData());
            }
            
            // run some background thread here to deal with listening for the receipt for the txhash


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
