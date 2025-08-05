import { z } from 'zod';

export const createSpendingResponseSchema = (t: any) =>
  z.object({
      id: z.number().int(),
      name: z.string().nonempty(t("domains.spending.errors.name.required")),
      amount: z
        .number({ invalid_type_error: t("domains.spending.errors.amount.required") })
        .positive(t("domains.spending.errors.amount.positive")),
      date: z.string().nonempty(t("domains.spending.errors.date.required")),
      iconCategory: z.string().optional(),
      categoryName: z.string().optional(),
      periodic : z.boolean().optional()
    })
    


export type SpendingResponse = z.infer<ReturnType<typeof createSpendingResponseSchema>>;