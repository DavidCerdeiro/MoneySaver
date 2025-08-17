import { useTranslation } from "react-i18next";
import { BarChartVerticalComponent } from "../components/BarChartVerticalComponent";
import { PieChartComponent } from "../components/PieChartComponent";
import { useEffect, useState } from "react";
import type { ChartCategory } from "../schemas/CharCategory";
import { loadCategories } from "../application/ChartService";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { RadarChartComponent } from "../components/RadarChartComponent";
import { Separator } from "../../shared/components/separator";
import { Button } from "../../shared/components/button";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { addMonths, subMonths } from "date-fns";

export function ViewChartsPage() {
    const { t } = useTranslation();
    const today = new Date();
    const todayMonth = today.getMonth() + 1;
    const todayYear = today.getFullYear();
    
    const [chartData, setChartData] = useState<ChartCategory[]>([]);
    const [currentDate, setCurrentDate] = useState(new Date());
    const selectedMonth = currentDate.getMonth() + 1;
    const selectedYear = currentDate.getFullYear();

    const isCurrentMonth = selectedMonth === todayMonth && selectedYear === todayYear;
    const handlePrevMonth = () => setCurrentDate(subMonths(currentDate, 1));
    const handleNextMonth = () => {
        if (!isCurrentMonth) {
          setCurrentDate(addMonths(currentDate, 1));
        }
    };

    useEffect(() => {
      async function fetchCategories() {
        const categories = await loadCategories(selectedMonth, selectedYear);
        if (categories) {
          setChartData(categories);
        }
      }
  
      fetchCategories();
    }, [selectedMonth, selectedYear]);
    
  return (
    <DefaultPageLayout>
      <div>
        <h1 className="page-title">{t("domains.charts.view.title")}</h1>
        <p className="page-description">{t("domains.charts.view.description", { month: selectedMonth, year: selectedYear })}</p>
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
            <div className="row-chart">
                <RadarChartComponent data={chartData} principalPage={false} />
                <PieChartComponent data={chartData} principalPage={false} />
            </div>
            <Separator className="my-4" />
            <div className="mt-2">
                <BarChartVerticalComponent data={chartData} principalPage={false} />
            </div>
      </div>
    </DefaultPageLayout>
  );
}
