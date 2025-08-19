import { Bar, BarChart, CartesianGrid, XAxis } from "recharts"

import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/app/domains/shared/components/card"
import type { ChartConfig } from "@/app/domains/shared/components/chart"
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/app/domains/shared/components/chart"
import type { CompareChart } from "../schemas/CompareChart"
import { useTranslation } from 'react-i18next'

type Props = {
  dataChart: CompareChart[];
  monthName1: string;
  monthName2: string;
};

const chartConfig = {
  desktop: {
    label: "Desktop",
    color: "var(--chart-1)",
  },
  mobile: {
    label: "Mobile",
    color: "var(--chart-2)",
  },
} satisfies ChartConfig

export function BarChartComparisonComponent({
  dataChart,
  monthName1,
  monthName2
}: Props) {
    const { t } = useTranslation()
  return (
    <Card className="chart-card">
      <CardHeader>
        <CardTitle>{t("domains.charts.type.bars")}</CardTitle>
      </CardHeader>
      <CardContent>
        <ChartContainer config={chartConfig}>
          <BarChart accessibilityLayer data={dataChart}>
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="category"
              tickLine={false}
              tickMargin={10}
              axisLine={false}
              fontSize={12}
              tickFormatter={(value) => value.slice(0, 15)}
              
            />
            <ChartTooltip
              cursor={false}
              content={<ChartTooltipContent indicator="line" nameKey="category" className="text-black"/>}
            />
            <Bar dataKey="month1" fill="var(--color-desktop)" radius={4} name={monthName1}/>
            <Bar dataKey="month2" fill="var(--color-mobile)" radius={4} name={monthName2}/>
          </BarChart>
        </ChartContainer>
      </CardContent>
    </Card>
  )
}
