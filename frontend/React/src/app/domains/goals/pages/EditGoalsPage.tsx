import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useTranslation } from "react-i18next";
import type { GoalData } from "../schemas/Goal";
import { useEffect, useState } from "react";
import { obtainAllGoalsFromUser } from "../application/GoalService";
import { fetchCategoriesForUser } from "../../category/application/CategoryService";
import type { CategoryData } from "../../category/schemas/Category";
import { GoalsTable } from "../components/GoalsTable";
import { EditGoalForm } from "../components/EditGoalForm";

export function EditGoalsPage() {
    const { t } = useTranslation();
    const [goals, setGoals] = useState<GoalData[]>([]);
    const [categories, setCategories] = useState<CategoryData[]>([]);
    const [currentDate] = useState(new Date());
    const selectedMonth = currentDate.getMonth() + 1;
    const selectedYear = currentDate.getFullYear();
    
    
    const refreshGoals = () => {
      obtainAllGoalsFromUser({ month: selectedMonth, year: selectedYear })
        .then((data) => setGoals(data))
        .catch(console.error);
    };

    useEffect(() => {
      fetchCategoriesForUser()
        .then((data) => setCategories(data.categories))
        .catch(console.error);

      refreshGoals();
    }, []);
  return (
    <DefaultPageLayout>
      <h1 className="page-title">{t("domains.goal.edit.title")}</h1>
      <p className="page-description">{t("domains.goal.edit.description")}</p>
      
      <GoalsTable goals={goals} month={selectedMonth} year={selectedYear} isView={false} />
      <div className="mt-5">
        <EditGoalForm goals={goals} categories={categories} refreshGoals={refreshGoals} />
      </div>
    </DefaultPageLayout>
  );
}