import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useTranslation } from "react-i18next";
import type { GoalData } from "../schemas/Goal";
import { useEffect, useState } from "react";
import { obtainAllGoalsFromUser } from "../application/GoalService";
import { GoalsTable } from "../components/GoalsTable";
import { Button } from "../../shared/components/button";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { addMonths, subMonths } from "date-fns";

export function ViewGoalsPage() {
    const { t } = useTranslation();
    const [goals, setGoals] = useState<GoalData[]>([]);
    const [currentDate, setCurrentDate] = useState(new Date());
    const selectedMonth = currentDate.getMonth() + 1;
    const selectedYear = currentDate.getFullYear();

    const today = new Date();
    const todayMonth = today.getMonth() + 1;
    const todayYear = today.getFullYear();

    const isCurrentMonth = selectedMonth === todayMonth && selectedYear === todayYear;

    let goalsBelow100 = goals.filter((g) => (g.percent ?? 0) < 100).length;
    let totalGoals = goals.length;

    useEffect(() => {
        obtainAllGoalsFromUser({ month: selectedMonth, year: selectedYear })
        .then((data) => setGoals(data))
        .catch(console.error);
        goalsBelow100 = goals.filter((g) => (g.percent ?? 0) < 100).length;
        totalGoals = goals.length;
    }, [selectedMonth, selectedYear]);

    const handlePrevMonth = () => setCurrentDate(subMonths(currentDate, 1));
    const handleNextMonth = () => {
        if (!isCurrentMonth) {
        setCurrentDate(addMonths(currentDate, 1));
        }
    };
    return (
        <DefaultPageLayout>
            <h1 className="page-title">{t("domains.goal.view.title")}</h1>
            <p className="page-description">{t("domains.goal.view.description")}</p>
            <p className="page-description">{t("domains.goal.view.stats", { month: selectedMonth, year: selectedYear, goalsBelow100, totalGoals })}</p>
            <div className="flex flex-col sm:flex-row items-center justify-between gap-2 my-4">
  <Button
    onClick={handlePrevMonth}
    variant="outline"
    className="flex bg-black text-white w-full sm:w-auto justify-center"
  >
    <ChevronLeft className="mr-2" />
    {t("domains.spending.view.previousMonth")}
  </Button>
  <Button
    onClick={handleNextMonth}
    variant="outline"
    className="flex bg-black text-white w-full sm:w-auto justify-center"
    disabled={isCurrentMonth}
  >
    {t("domains.spending.view.nextMonth")}
    <ChevronRight className="ml-2" />
  </Button>
</div>
            <GoalsTable goals={goals} month={selectedMonth} year={selectedYear} isView />
        </DefaultPageLayout>
    );
}
