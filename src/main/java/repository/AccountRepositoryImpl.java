package repository;


public class AccountRepositoryImpl implements AccountRepository {
    private final String ACCOUNTS = ".\\src\\main\\resources\\Accounts";
    private final AccountRepository accountRepository;
    public AccountRepositoryImpl() {
       accountRepository = new AccountRepositoryImpl();
    }
/*    public void save(Account account) throws IOException {
       if (account.getId() <= 0) {
            account.setId(findMaxId() + 1);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS, true));
        if (new File(CUSTOMERS).length() != 0) {
            writer.write("\n");
        }
        writer.write(account.getId() + ";" + account.getName() + ";" + specialtyRepository.specialtiesToStringForBase(account.getSpecialties()) + ";" + account.getAccount().getAccountStatus());
        writer.flush();
    }
}
