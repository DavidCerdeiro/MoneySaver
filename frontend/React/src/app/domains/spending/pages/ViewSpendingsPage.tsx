import { useTranslation } from 'react-i18next';
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useEffect, useState } from "react";
import { SpendingsTable } from '../components/SpendingsTable';
import { obtainAllSpendingsByMonthAndYear } from "../application/SpendingService";
import type { SpendingResponse } from '../schemas/SpendingResponse';

import { Button } from '../../shared/components/button';
import { ChevronLeft, ChevronRight } from "lucide-react";
import { addMonths, subMonths } from "date-fns";
import type { TransactionResponse } from '../../transactions/schemas/TransactionResponse';
import { getAllUserTransactionsByMonthAndYear } from '../../transactions/application/TransactionService';
import { TransactionsTable } from '../../transactions/components/TransactionTable';

export function ViewSpendingsPage() {
  const { t } = useTranslation();
  const [spendings, setSpendings] = useState<SpendingResponse[]>([]);
  const [transactions, setTransactions] = useState<TransactionResponse[]>([]);
  const [currentDate, setCurrentDate] = useState(new Date());
  const selectedMonth = currentDate.getMonth() + 1;
  const selectedYear = currentDate.getFullYear();

  const today = new Date();
  const todayMonth = today.getMonth() + 1;
  const todayYear = today.getFullYear();

  const isCurrentMonth = selectedMonth === todayMonth && selectedYear === todayYear;

  useEffect(() => {
    obtainAllSpendingsByMonthAndYear({ month: selectedMonth, year: selectedYear })
      .then((data) => {
        setSpendings(data.spendings);
      })
      .catch(console.error);

    getAllUserTransactionsByMonthAndYear(selectedMonth, selectedYear)
      .then((data) => {
        setTransactions(data);
      })
      .catch(console.error);
  }, [selectedMonth, selectedYear]);

  const handlePrevMonth = () => setCurrentDate(subMonths(currentDate, 1));
  const handleNextMonth = () => {
    if (!isCurrentMonth) {
      setCurrentDate(addMonths(currentDate, 1));
    }
  };
  return (
     <DefaultPageLayout>
      <div className="mobile-spacing">
        <h1 className="page-title">
          {t('domains.spending.view.title')}
        </h1>
        <p className="page-description">
          {t('domains.spending.view.description')}
        </p>
        <p className="page-description">
          {t('domains.spending.view.date', { month: selectedMonth, year: selectedYear })}
        </p>
        
        <div className="flex flex-col sm:flex-row items-center justify-between gap-2 my-6">
          <Button
            onClick={handlePrevMonth}
            variant="outline"
            className="button-neutral w-full sm:w-auto"
          >
            <ChevronLeft className="mr-2 h-4 w-4" />
            {t("domains.spending.view.previousMonth")}
          </Button>
          <Button
            onClick={handleNextMonth}
            variant="outline" 
            className="button-neutral w-full sm:w-auto"
            disabled={isCurrentMonth}
          >
            {t("domains.spending.view.nextMonth")}
            <ChevronRight className="ml-2 h-4 w-4" />
          </Button>
        </div>

        <div className="table-container">
          <SpendingsTable spendings={spendings} />
        </div>
        
        <div className="table-container mt-8">
          <TransactionsTable transactions={transactions} />
        </div>
      </div>
    </DefaultPageLayout>
  );
}
