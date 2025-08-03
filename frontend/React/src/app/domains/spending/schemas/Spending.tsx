import { z } from 'zod';

export const createSpendingSchema = (t: any) =>
  z.object({
      name: z.string().nonempty(t("domains.spending.errors.name.required")),
      idUser: z.number().int(),
      amount: z
        .number({ invalid_type_error: t("domains.spending.errors.amount.required") })
        .positive(t("domains.spending.errors.amount.positive")),
      isPeriodic: z.boolean(),
      date: z.string().nonempty(t("domains.spending.errors.date.required")),
      expirationDate: z.string().optional(),
      typePeriodic: z.number().optional(),
      idCategory: z.number().optional(),
    })
    .refine((data) => {
      // Ensure that if isPeriodic is true, expirationDate is provided
      if (data.isPeriodic) return !!data.expirationDate;
      return true;
    }, {
      path: ["expirationDate"],
      message: t("domains.spending.errors.expirationDate.required"),
    })
    .refine((data) => {
      // Ensure that if isPeriodic is true, typePeriodic is provided
      if (data.isPeriodic) return !!data.typePeriodic;
      return true;
    }, {
      path: ["typePeriodic"],
      message: t("domains.spending.errors.typePeriodic.required"),
    })
    .refine((data) => {
      // Ensure that if expirationDate is provided, it is greater than or equal to today
      if (data.isPeriodic && data.expirationDate) {
        const today = new Date().setHours(0, 0, 0, 0);
        const expiration = new Date(data.expirationDate).setHours(0, 0, 0, 0);
        return expiration >= today;
      }
      return true;
    }, {
      path: ["expirationDate"],
      message: t("domains.spending.errors.expirationDate.future"),
    });


export type SpendingData = z.infer<ReturnType<typeof createSpendingSchema>>;