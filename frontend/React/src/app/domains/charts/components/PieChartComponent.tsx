import { Pie, PieChart } from "recharts"

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/app/domains/shared/components/card"
import type { ChartConfig } from "@/app/domains/shared/components/chart"
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/app/domains/shared/components/chart"
import type { ChartCategory } from "../schemas/CharCategory"
import { useTranslation } from "react-i18next"

type Props = {
  data: ChartCategory[];
  principalPage: boolean;
};

const chartConfig = {
  desktop: {
    label: "Money",
    color: "var(--chart-1)",
  },
} satisfies ChartConfig

export function PieChartComponent({ data, principalPage }: Props) {
  const { t } = useTranslation();
  const today = new Date();
  const monthName = today.toLocaleString("en-EN", { month: "long" }).toLowerCase();
    
  let title = "";
  if(principalPage){
    title = t("domains.charts.main.title");
  } else {
    title = t("domains.charts.type.pie");
  }

  return (
    <Card className="chart-card">
      <CardHeader className="items-center pb-0">
        <CardTitle >{title}</CardTitle>
        <CardDescription className="card-description" hidden={!principalPage}>{t(`months.${monthName}`)} - {today.getFullYear()}</CardDescription>
      </CardHeader>
      <CardContent className="flex-1 pb-0">
        <ChartContainer
          config={chartConfig}
          className="mx-auto aspect-square max-h-[250px]"
        >
          <PieChart>
            <ChartTooltip
              cursor={false}
              content={<ChartTooltipContent hideIndicator nameKey="name" className="bg-gray-200 text-gray-500" />}
            />
            <Pie data={data} dataKey="total" nameKey="name" />
          </PieChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col items-start gap-2 text-sm mt-4" hidden={!principalPage}>
        <div className="flex gap-2 leading-none font-medium">
          {t("domains.charts.main.footer")}
        </div>
      </CardFooter>
    </Card>
  )
}
