import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { useTranslation } from "react-i18next";
import { addAccounts } from "../../account/application/AccountService";
import { extractTransactions } from "../../transactions/application/TransactionService";
import { createFetchAccountSchema } from "../../account/schemas/FetchAccount";
import { ClipLoader } from "react-spinners";
import { createExtractTransactionSchema } from "../../transactions/schemas/ExtractTransactionData";
export function CallbackPage() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const schemaTransaction = createExtractTransactionSchema(t);
  const schemaAccounts = createFetchAccountSchema();

  useEffect(() => {
    const handleCallback = async () => {
      const params = new URLSearchParams(window.location.search);
      const code = params.get("code");
      const state = params.get("state");
      if (!code || !state) return;

      setLoading(true);

      if (state === "accounts") {
        const request = schemaAccounts.safeParse({ code });
        if (!request.success) return;

        try {
          const response = await addAccounts(request.data);
          if(response.length === 1) {
            toast.success(t("domains.account.addedOneAccount", { name: response.length }));
          } else {
            toast.success(t("domains.account.addedSuccesfully", { num: response.length }));
          }
        } catch (error: unknown) {
          if (error instanceof Error && error.message === "No accounts found") {
            toast.error(t("domains.account.noAccountsFound"));
          } else {
            console.error(error);
          }
        } finally {
          navigate("/user/accounts", { replace: true });
          setLoading(false); 
        }

      } else if (state === "transactions") {
        const minDate = localStorage.getItem('minDate');
        const maxDate = localStorage.getItem('maxDate');
        const accountString = localStorage.getItem('account');
        const account = accountString ? JSON.parse(accountString) : undefined;
        const request = schemaTransaction.safeParse({ code, minDate, maxDate, account });
        if (!request.success) return;
        
        try {
          const response = await extractTransactions(request.data);
          console.log("Transacciones extraídas: ", response.transactions);
          setLoading(false);
          navigate("/transactions/add", {
            state: { transactions: response.transactions }
          });
        } catch (error: unknown) {
          if (error instanceof Error) {
            if(error.message === "No transactions found") {
              toast.error(t("domains.transaction.extract.noTransactionsFound"));
              navigate("/spendings/transactions");
            } else {
              console.error(error);
            }
          }
        }
      }
    };

    handleCallback();
  }, [navigate, t]);

  return (
    <>
      {loading && (
        <div className="fixed inset-0 flex items-center justify-center bg-black z-50">
          <ClipLoader color="#fff" size={60} />
        </div>
      )}
    </>
  );
};

