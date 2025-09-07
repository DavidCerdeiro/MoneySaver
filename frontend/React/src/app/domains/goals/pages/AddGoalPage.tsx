import { useEffect, useState } from "react";
import { getCategories } from "../../category/application/CategoryService";
import type { CategoryData } from "../../category/schemas/Category";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout"
import { useTranslation } from "react-i18next";
import { AddGoalForm } from "../components/AddGoalForm";
import { GoalsTable } from "../components/GoalsTable";
import type { GoalData } from "../schemas/Goal";
import { obtainAllGoalsFromUser } from "../application/GoalService";

export function AddGoalPage() {
  const { t } = useTranslation();
  const [categories, setCategories] = useState<CategoryData[]>([]);
  const [goals, setGoals] = useState<GoalData[]>([]);
  const [currentDate] = useState(new Date());
  const selectedMonth = currentDate.getMonth() + 1;
  const selectedYear = currentDate.getFullYear();
  
  const refreshGoals = () => {
      obtainAllGoalsFromUser({ month: selectedMonth, year: selectedYear })
        .then((data) => setGoals(data))
        .catch(console.error);
  };

  useEffect(() => {
    getCategories()
      .then((data) => setCategories(data.categories))
      .catch(console.error);

    refreshGoals();
  }, []);
  return (
    <DefaultPageLayout>
      <h1 className="page-title">{t("domains.goal.add.title")}</h1>
      <p className="page-description">{t("domains.goal.add.description")}</p>
      <div className="table-container">
        <GoalsTable goals={goals} isView={false} />
      </div>
      <AddGoalForm categories={categories} refreshGoals={refreshGoals} />
    </DefaultPageLayout>
  );
}