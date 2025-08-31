import { useTranslation } from "react-i18next";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { fetchAccountsForUser } from "../../account/application/AccountService";
import type { AccountData } from "../../account/schemas/Account";
import { useEffect, useState } from "react";
import { ExtractTransactionsForm } from "../components/ExtractTransactionsForm";

export function ExtractTransactionsPage() {
  const { t } = useTranslation();
  const [accounts, setAccounts] = useState<AccountData[]>([]);

  const loadAccounts = async () => {
    try {
      const data = await fetchAccountsForUser();
      setAccounts(data.accounts);
    } catch (error) {
      console.error("Error fetching accounts:", error);
    }
  };

  useEffect(() => {
    loadAccounts();
  }, []);
  return (
    <DefaultPageLayout>
      <h1 className="page-title">{t("domains.transaction.extract.title")}</h1>
      <p className="page-description">{t("domains.transaction.extract.description")}</p>
      <ExtractTransactionsForm accounts={accounts} />
    </DefaultPageLayout>
  );
}