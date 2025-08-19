import { z } from "zod";

export const createCompareChartDataSchema = (t: any) => 
    z.object({
    month1: z.string().nonempty(t("domains.spending.errors.name.required")),
    month2: z.string().nonempty(t("domains.spending.errors.name.required")),
});

export type CompareChartData = z.infer<ReturnType<typeof createCompareChartDataSchema>>;