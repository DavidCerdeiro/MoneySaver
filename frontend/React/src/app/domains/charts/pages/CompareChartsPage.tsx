import { useEffect, useState } from "react";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useTranslation } from 'react-i18next'
import type { CompareChart } from "../schemas/CompareChart";
import { obtainComparisonCategories } from "../../category/application/CategoryService";
import { BarChartComparisonComponent } from "../components/BarChartComparisonComponent";
import { RadarChartComparisonComponent } from "../components/RadarChartComparisonComponent";
import { getEmojiById } from "../../category/components/EmojiFunctions";
import { Input } from "../../shared/components/input";
import { Label } from "../../shared/components/label";
import { zodResolver } from "@hookform/resolvers/zod";
import { createCompareChartDataSchema, type CompareChartData } from "../schemas/CompareChartData";
import { useForm} from 'react-hook-form';

export function CompareChartsPage() {
  const { t } = useTranslation();
  const schema = createCompareChartDataSchema(t);

  const now = new Date();
  const currentMonth = now.getMonth() + 1;
  const currentYear = now.getFullYear();
  const prevMonthDate = new Date(now.getFullYear(), now.getMonth() - 1, 1);
  const prevMonth = prevMonthDate.getMonth() + 1;
  const prevYear = prevMonthDate.getFullYear();

  const [chartData, setChartData] = useState<CompareChart[]>([]);
  const [monthNames, setMonthNames] = useState({ month1: "", month2: "" });
  const [selectedYears, setSelectedYears] = useState({ year1: currentYear, year2: prevYear });
  const [numericMonth, setNumericMonth] = useState({ month1: currentMonth, month2: prevMonth });

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CompareChartData>({
    resolver: zodResolver(schema),
    defaultValues: {
      month1: `${String(currentYear)}-${String(currentMonth).padStart(2, "0")}`,
      month2: `${String(prevYear)}-${String(prevMonth).padStart(2, "0")}`,
    },
  });

  const fetchCategories = async (month1: number, year1: number, month2: number, year2: number) => {
    const categories = await obtainComparisonCategories({ month1, year1, month2, year2 });

    const combined: CompareChart[] = categories.month1.map((cat1, index) => {
      const cat2 = categories.month2[index];
      return {
        category: `${getEmojiById(cat1.icon)} ${cat1.name}`,
        month1: cat1.totalSpending || 0,
        month2: cat2.totalSpending || 0,
      };
    });

    
    const name1 = new Date(year1, month1 - 1).toLocaleString("default", { month: "long" });
    const name2 = new Date(year2, month2 - 1).toLocaleString("default", { month: "long" });

    setMonthNames({ month1: name1, month2: name2 });
    setChartData(combined);
  };

  
  useEffect(() => {
    fetchCategories(currentMonth, currentYear, prevMonth, prevYear);
  }, []);

  
  const onSubmit = async (data: CompareChartData) => {
    const [year1, month1] = data.month1.split("-");
    const [year2, month2] = data.month2.split("-");
    fetchCategories(Number(month1), Number(year1), Number(month2), Number(year2));
    setSelectedYears({ year1: Number(year1), year2: Number(year2) });
    setNumericMonth({ month1: Number(month1), month2: Number(month2) });
  };

  return (
    <DefaultPageLayout>
      <h1 className="page-title">{t("domains.charts.compare.title")}</h1>
      <p className="page-description">
          {t("domains.charts.compare.description", {
            
          })}
      </p>
    <p className="page-description">{t("domains.charts.compare.actualMonths", {
        month1: numericMonth.month1,
        year1: selectedYears.year1,
        month2: numericMonth.month2,
        year2: selectedYears.year2
      })}</p>
      <div className="general-container"> 
      <form onSubmit={handleSubmit(onSubmit)} className="mb-6">
        
          <div className="row-input">
            <div className="w-full">
              <Label htmlFor="date1" className="mb-2">{t("domains.charts.compare.month1Label")}</Label>
              <Input id="date1" type="month" className="input-form" {...register("month1")} />
              {errors.month1 && <p className="text-red-500 text-sm">{errors.month1.message}</p>}
            </div>
            <div className="w-full">
              <Label htmlFor="date2" className="mb-2">{t("domains.charts.compare.month2Label")}</Label>
              <Input id="date2" type="month" className="input-form" {...register("month2")} />
              {errors.month2 && <p className="text-red-500 text-sm">{errors.month2.message}</p>}
            </div>
          </div>
          <div className="flex justify-center mb-4">
              <button type="submit" className="button-green mt-4">
                  {t("domains.charts.compare.submit")}
              </button>
          </div>
          
        </form>

      
        <div className="mb-5">
          <BarChartComparisonComponent
            dataChart={chartData}
            monthName1={monthNames.month1}
            monthName2={monthNames.month2}
          />
        </div>
        <div>
          <RadarChartComparisonComponent
            dataChart={chartData}
            month1={monthNames.month1}
            month2={monthNames.month2}
          />
        </div>
      </div>
    </DefaultPageLayout>
  );
}
