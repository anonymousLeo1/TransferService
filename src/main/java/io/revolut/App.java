package io.revolut;

import org.jooby.Jooby;
import io.revolut.api.AccountApiController;
import io.revolut.domain.*;
import org.jooby.json.Jackson;

public class App extends Jooby {

  {
    use(new Jackson());

    use(AccountApiController.class);
    use((env, conf, binder) -> {
            binder.bind(AccountRepository.class).to(CachedAccountRepository.class);
        });

	  onStart(reg -> TestAccountsPopulation.populateAccounts(reg.require(AccountRepository.class)));

  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
