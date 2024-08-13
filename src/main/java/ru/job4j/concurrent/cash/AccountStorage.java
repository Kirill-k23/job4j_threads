package ru.job4j.concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe

public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public void delete(int id) {
        accounts.remove(id);
    }

    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        boolean result = false;
        if (from.isPresent() && to.isPresent() && from.get().amount() >= amount) {
            update(new Account(fromId, from.get().amount() - amount));
            update(new Account(toId, to.get().amount() + amount));
            result = true;
        }
        return result;
    }
}
