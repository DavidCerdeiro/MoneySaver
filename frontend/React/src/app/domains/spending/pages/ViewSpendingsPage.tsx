import { useTranslation } from 'react-i18next';
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useEffect, useState } from "react";
import { SpendingsTable } from '../components/SpendingsTable';
import { obtainAllSpendingsByMonthAndYear } from "../application/SpendingService";
import type { SpendingResponse } from '../schemas/SpendingResponse';

import { Button } from '../../shared/components/button';
import { ChevronLeft, ChevronRight } from "lucide-react";
import { addMonths, subMonths } from "date-fns";

export function ViewSpendingsPage() {
  const { t } = useTranslation();
  const [spendings, setSpendings] = useState<SpendingResponse[]>([]);
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
  }, [selectedMonth, selectedYear]);

  const handlePrevMonth = () => setCurrentDate(subMonths(currentDate, 1));
  const handleNextMonth = () => {
    if (!isCurrentMonth) {
      setCurrentDate(addMonths(currentDate, 1));
    }
  };
  return (
    <div>
      <DefaultPageLayout>
        <h1 className="page-title">
          {t('domains.spending.view.title')}
        </h1>
        <p className="page-description">
          {t('domains.spending.view.description')}
        </p>
        <div className="flex items-center justify-between my-4">
          <Button
            onClick={handlePrevMonth}
            variant="outline"
            className="flex bg-black text-white"
          >
            <ChevronLeft className="mr-2" />
            {t("domains.spending.view.previousMonth")}
          </Button>
          <Button
            onClick={handleNextMonth}
            variant="outline"
            className="flex bg-black text-white"
            disabled={isCurrentMonth}
          >
            {t("domains.spending.view.nextMonth")}
            <ChevronRight className="ml-2" />
          </Button>
        </div>

        <SpendingsTable
          spendings={spendings}
          month={selectedMonth}
          year={selectedYear}
        />
      </DefaultPageLayout>
    </div>
  );
}
