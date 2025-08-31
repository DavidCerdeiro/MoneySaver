import { Bar, BarChart, CartesianGrid, LabelList, XAxis } from "recharts"

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/app/domains/shared/components/card"
import { ChartContainer, ChartTooltip, ChartTooltipContent } from "@/app/domains/shared/components/chart"
import type { ChartConfig } from "@/app/domains/shared/components/chart"
import type { ChartCategory } from "../schemas/ChartCategory"
import { useTranslation } from 'react-i18next'


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

export function BarChartVerticalComponent({ data, principalPage }: Props) {
  const { t } = useTranslation();

  const today = new Date();
  const monthName = today.toLocaleString("en-EN", { month: "long" }).toLowerCase();

  let title = "";
  if(principalPage){
    title = t("domains.charts.main.title");
  } else {
    title = t("domains.charts.type.bars");
  }
  
  return (
    <Card className="chart-card">
      <CardHeader>
        <CardTitle>{title}</CardTitle>
        <CardDescription className="card-description" hidden={!principalPage}>{t(`months.${monthName}`)} - {today.getFullYear()}</CardDescription>
      </CardHeader>
      <CardContent> 
        <ChartContainer config={chartConfig}>
          <BarChart
            accessibilityLayer
            data={data}
            className="w-full h-[500px]"
            margin={{ top: 35, left: 10, right: 10, bottom: 10 }}
          >
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="name"
              tickLine={false}
              tickMargin={10}
              axisLine={false}
              style={{ fill: "white", fontSize: 12, fontWeight: 500 }}
              tickFormatter={(value) => value.slice(0, 15)}
            />
            <ChartTooltip
              cursor={false}
              content={<ChartTooltipContent className="bg-gray-100 text-gray-500" />}
            />
            <Bar dataKey="total" fill="#3b82f6" radius={8}>
              <LabelList
                dataKey="totalLabel"
                position="top"
                offset={12}
                style={{ fill: "white", fontSize: 12, fontWeight: 500 }}
                fontSize={12}
              />
            </Bar>
          </BarChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col items-start gap-2 text-sm mt-10" hidden={!principalPage}>
        <div className="flex gap-2 leading-none font-medium">
          {t("domains.charts.main.footer")}
        </div>
      </CardFooter>
    </Card>
  )
}
