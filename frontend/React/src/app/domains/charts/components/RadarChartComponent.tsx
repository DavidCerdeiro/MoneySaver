import { PolarAngleAxis, PolarGrid, Radar, RadarChart } from "recharts"

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
import type { ChartCategory } from "../schemas/ChartCategory"
import { useTranslation } from "react-i18next"

export const description = "A radar chart"

const chartConfig = {
  desktop: {
    label: "Desktop",
    color: "var(--chart-1)",
  },
} satisfies ChartConfig

type Props = {
  data: ChartCategory[];
  principalPage: boolean;
};

export function RadarChartComponent({ data, principalPage }: Props) {
  const { t } = useTranslation();

  const today = new Date();
  const monthName = today.toLocaleString("en-EN", { month: "long" }).toLowerCase();

  let title = "";
  if(principalPage){
    title = t("domains.charts.main.title");
  } else {
    title = t("domains.charts.type.radar");
  }

  return (
    <Card className="chart-card">
      <CardHeader className="items-center pb-4">
        <CardTitle>{title}</CardTitle>
        <CardDescription className="card-description" hidden={!principalPage}>{t(`months.${monthName}`)} - {today.getFullYear()}</CardDescription>
      </CardHeader>
      <CardContent className="pb-0">
        <ChartContainer
          config={chartConfig}
          className="mx-auto aspect-square max-h-[250px]"
        >
          <RadarChart data={data}>
            <ChartTooltip cursor={false} content={<ChartTooltipContent hideIndicator nameKey="name" className="bg-gray-200 text-gray-500"/>} />
            <PolarAngleAxis dataKey="name" tickFormatter={(value) => value.slice(0, 2)}/>
            <PolarGrid/>
            <Radar
              dataKey="total"
              fill="#3b82f6"
              fillOpacity={0.6}
            />
          </RadarChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col gap-2 text-sm" hidden={!principalPage}>
        <div className="flex gap-2 leading-none font-medium">
          {t("domains.charts.main.footer")}
        </div>
      </CardFooter>
    </Card>
  )
}
