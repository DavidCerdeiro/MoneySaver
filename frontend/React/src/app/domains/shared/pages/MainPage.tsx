import { useEffect, useState } from "react";
import { BarChartVerticalComponent } from "../../charts/components/BarChartVerticalComponent"
import { PieChartComponent } from "../../charts/components/PieChartComponent";
import { RadarChartComponent } from "../../charts/components/RadarChartComponent";
import { DefaultPageLayout } from "../layouts/DefaultPageLayout"
import type { ChartCategory } from "../../charts/schemas/CharCategory";
import { useTranslation } from 'react-i18next';
import { loadCategories } from "../../charts/application/ChartService";
import { loadFavouriteTypeCharts } from "../../user/application/UserService";

export function MainPage() {
  const { t } = useTranslation();
  const today = new Date();
  const todayMonth = today.getMonth() + 1;
  const todayYear = today.getFullYear();

  const [chartData, setChartData] = useState<ChartCategory[]>([]);
  const [favouriteTypeCharts, setFavouriteTypeCharts] = useState<any>([]);
  async function fetchCategories() {
      const categories = await loadCategories(todayMonth, todayYear);
      if (categories) {
        setChartData(categories);
      }
  }

  async function fetchFavouriteTypeCharts() {
    const favourite = await loadFavouriteTypeCharts();
    if (favourite) {
      setFavouriteTypeCharts(favourite);
      localStorage.setItem("favouriteTypeCharts", JSON.stringify(favourite));
    }
  }

  useEffect(() => {
    const stored = localStorage.getItem("favouriteTypeCharts");
    
    if (stored !== null && stored !== "undefined") {
      setFavouriteTypeCharts(stored);
    } else {
      fetchFavouriteTypeCharts();
    }
    fetchCategories();
  }, []);

   const renderChart = () => {
    switch (favouriteTypeCharts) {
      case "1":
        return <BarChartVerticalComponent data={chartData} principalPage={true} />;
      case "2":
        return <RadarChartComponent data={chartData} principalPage={true} />;
      case "3":
        return <PieChartComponent data={chartData} principalPage={true} />;
      default:
        return <BarChartVerticalComponent data={chartData} principalPage={true} />;
    }
  };
  return (
    <DefaultPageLayout>
      <h1 className="page-title">{t("app.welcome")}</h1>
      <div className="flex justify-center items-center mt-8 w-full px-4">
        {renderChart()}
      </div>
    </DefaultPageLayout>
  )
}
