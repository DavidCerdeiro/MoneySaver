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
  const state = 'transactions';
  const transactionsUrl = `https://auth.truelayer-sandbox.com/?response_type=code&client_id=sandbox-moneysaver-fefb0e&scope=accounts%20transactions&redirect_uri=http://localhost:5173/callback&providers=uk-cs-mock%20uk-ob-all%20uk-oauth-all&state=${state}`
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
    <form onSubmit={handleSubmit(onSubmitForm)}>
      <Label htmlFor="account" className="label">{t('domains.account.title')}</Label>
      <AccountsTable accounts={accounts} isAccountPage={false} onSelect={(account) => setValue("account", account)}/>
      <div className="row-input mt-3 mb-3">
        <div className="w-full">
          <Label htmlFor="minDate" className="label">{t('domains.transaction.extract.minDate')}</Label>
          <Input id="minDate" type="date" {...register("minDate")} className="input-dark" />
          {errors.minDate && <p className="text-red-500 text-sm">{errors.minDate.message}</p>}
        </div>
            
        <div className="w-full">
          <Label htmlFor="maxDate" className="label">{t('domains.transaction.extract.maxDate')}</Label>
          <Input id="maxDate" type="date" {...register("maxDate")} className="input-dark" />
          {errors.maxDate && <p className="text-red-500 text-sm">{errors.maxDate.message}</p>}
        </div>
      </div>
      <div className="flex justify-center mt-5">
        <Button type="submit" className="flex" disabled={!watch("account") || !watch("minDate") || !watch("maxDate")}>{t('domains.transaction.extract.submit')}</Button>
      </div>
    </form>
  );
}
