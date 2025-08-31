import { useTranslation } from "react-i18next";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useEffect, useState } from "react";
import type { AccountData } from "../schemas/Account";
import { deleteAccount, fetchAccountsForUser } from "../application/AccountService";
import { AccountsTable } from "../components/AccountsTable";
import { Button } from "../../shared/components/button";
import { toast } from "sonner";

export const AccountsPage = () => {
    const { t } = useTranslation();
    const [accounts, setAccounts] = useState<AccountData[]>([]);
    const state = 'accounts';

    localStorage.setItem('truelayer_oauth_state', state); 

    const accountsUrl = `https://auth.truelayer-sandbox.com/?response_type=code&client_id=sandbox-moneysaver-fefb0e&scope=accounts&redirect_uri=http://localhost:5173/callback&providers=uk-cs-mock&state=${state}`;

    const refreshAccounts = async () => {
        try {
            const data = await fetchAccountsForUser();
            setAccounts(data.accounts);
        } catch (error) {
            console.error("Error fetching accounts:", error);
        }
    };

    const onDelete = async (id: number) => {
        try {
            await deleteAccount(id);
            toast.success(t("domains.account.delete.success"));
            refreshAccounts();
        } catch (error) {
            toast.error(t("domains.account.delete.error"));
        }
    };

    useEffect(() => {
        refreshAccounts();
    }, []);

    return (
        <DefaultPageLayout>
            <h1 className="page-title">{t("domains.account.page.title")}</h1>
            <p className="page-description">{t("domains.account.page.description")}</p>
            <AccountsTable accounts={accounts} onDelete={onDelete} isAccountPage={true} />
            <div className="button-container">
                <a
                    href={accountsUrl}
                    rel="noopener noreferrer"
                >
                    <Button>{t("domains.account.page.linkAccounts")}</Button>
                </a>
            </div>
        </DefaultPageLayout>
    );
};
