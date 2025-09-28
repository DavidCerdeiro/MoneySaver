import { PolarAngleAxis, PolarGrid, Radar, RadarChart } from "recharts"

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
import { useTranslation } from "react-i18next"
import type { CompareChart } from "../schemas/CompareChart"

type Props = {
  dataChart: CompareChart[];
  month1: string;
  month2: string;
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

export function RadarChartComparisonComponent({
  dataChart,
  month1,
  month2
}: Props) {
    const { t } = useTranslation()
  return (
    <Card className="chart-card">
      <CardHeader className="items-center pb-4">
        <CardTitle>{t("domains.charts.type.radar")}</CardTitle>
      </CardHeader>
      <CardContent className="pb-0">
        <ChartContainer
          config={chartConfig}
          className="mx-auto aspect-square"
        >
          <RadarChart
            data={dataChart}
            margin={{
              top: 30,
              right: 30,
              bottom: 30,
              left: 30,
            }}
          >
            <ChartTooltip
              cursor={false}
              content={<ChartTooltipContent indicator="line" nameKey="category" className="text-black"/>}
            />
            <PolarAngleAxis
              dataKey="category"
              tick={({ x, y, textAnchor, value, index, ...props }) => {
                const data = dataChart[index]

                return (
                  <text
                    x={x}
                    y={index === 0 ? y - 10 : y}
                    textAnchor={textAnchor}
                    fontSize={15}
                    fontWeight={500}
                    {...props}
                    fill="white"
                  >
                    <tspan>{data.month1}</tspan>
                    <tspan className="fill-muted-foreground">/</tspan>
                    <tspan>{data.month2}</tspan>
                    <tspan
                      x={x}
                      dy={"1rem"}
                      fontSize={12}
                      className="fill-muted-foreground"
                    >
                      {data.category?.slice(0, 2)}
                    </tspan>
                  </text>
                )
              }}
            />

            <PolarGrid />
            <Radar
              dataKey="month1"
              fill="var(--color-desktop)"
              fillOpacity={0}
              name={month1}
              strokeWidth={4}
            />
            <Radar 
              dataKey="month2" 
              fill="var(--color-mobile)" 
              fillOpacity={0}
              name={month2} 
              strokeWidth={4}/>
          </RadarChart>
        </ChartContainer>
      </CardContent>
    </Card>
  )
}
