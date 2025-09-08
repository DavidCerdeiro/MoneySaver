import { useTranslation } from "react-i18next";
import { createExtractTransactionSchema, type ExtractTransactionData } from "../schemas/ExtractTransactionData";
import { useForm } from 'react-hook-form';
import { zodResolver } from "@hookform/resolvers/zod/dist/zod.js";
import { Input } from "../../shared/components/input";
import { Label } from "../../shared/components/label";
import type { AccountData } from "../../account/schemas/Account";
import { AccountsTable } from "../../account/components/AccountsTable";
import { Button } from "../../shared/components/button";

type ExtractTransactionsFormProps = {
  accounts: AccountData[];
};

export function ExtractTransactionsForm({ accounts }: ExtractTransactionsFormProps) {
  const { t } = useTranslation();
  const schema = createExtractTransactionSchema(t);
  const state = "transactions";
  const clientId = "sandbox-moneysaver-fefb0e";
  const baseAuthUrl = "https://auth.truelayer-sandbox.com/";

  const redirectUri = import.meta.env.VITE_TRUELAYER_REDIRECT_URI;
  const providers = import.meta.env.VITE_TRUELAYER_PROVIDERS;

  const transactionsUrl = `${baseAuthUrl}?response_type=code&client_id=${clientId}&scope=accounts%20transactions&redirect_uri=${redirectUri}&providers=${encodeURIComponent(providers)}&state=${state}`;
  const {
          register,
          handleSubmit,
          watch,
          setValue,
          formState: { errors},
      } = useForm<ExtractTransactionData>({
          resolver: zodResolver(schema),
          defaultValues: {
              account: undefined,
              minDate: undefined,
              maxDate: undefined,
          },
      });
  const onSubmitForm = (data: ExtractTransactionData) => {
    localStorage.setItem('truelayer_oauth_state', state);
    localStorage.setItem('minDate', data.minDate!);
    localStorage.setItem('maxDate', data.maxDate!);
    localStorage.setItem('account', JSON.stringify(data.account));
    window.location.href = transactionsUrl;
  };

  return (
    <>
    <div className="table-container">
      <AccountsTable
        accounts={accounts}
        isAccountPage={false}
        onSelect={(account) => setValue("account", account)}
      />
    </div>
    <div className="form-container">
    <form onSubmit={handleSubmit(onSubmitForm)}>
      <div className="row-input mt-3 mb-3">
        <div className="w-full">
          <Label htmlFor="minDate" className="label">{t('domains.transaction.extract.minDate')}</Label>
          <Input id="minDate" type="date" {...register("minDate")} className="input-form" />
          {errors.minDate && <p className="text-red-500 text-sm">{errors.minDate.message}</p>}
        </div>
            
        <div className="w-full">
          <Label htmlFor="maxDate" className="label">{t('domains.transaction.extract.maxDate')}</Label>
          <Input id="maxDate" type="date" {...register("maxDate")} className="input-form" />
          {errors.maxDate && <p className="text-red-500 text-sm">{errors.maxDate.message}</p>}
        </div>
      </div>
      <div className="flex justify-center mt-5">
        <Button type="submit" className="flex" disabled={!watch("account") || !watch("minDate") || !watch("maxDate")}>{t('domains.transaction.extract.submit')}</Button>
      </div>
    </form>
  </div>
  </>
  );
}
