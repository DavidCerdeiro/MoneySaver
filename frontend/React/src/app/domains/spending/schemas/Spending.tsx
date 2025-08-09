import { z } from 'zod';
import { EstablishmentDataSchema } from './EstablishmentData';

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
      establishment: EstablishmentDataSchema.optional(),
    })
    .refine((data) => {
      if (data.isPeriodic) return !!data.expirationDate;
      return true;
    }, {
      path: ["expirationDate"],
      message: t("domains.spending.errors.expirationDate.required"),
    })
    .refine((data) => {
      if (data.isPeriodic) return !!data.typePeriodic;
      return true;
    }, {
      path: ["typePeriodic"],
      message: t("domains.spending.errors.typePeriodic.required"),
    })
    .refine((data) => {
      if (data.isPeriodic && data.expirationDate) {
        const today = new Date().setHours(0, 0, 0, 0);
        const expiration = new Date(data.expirationDate).setHours(0, 0, 0, 0);
        return expiration >= today;
      }
      return true;
    }, {
      path: ["expirationDate"],
      message: t("domains.spending.errors.expirationDate.future"),
    })
    .refine((data) => {
      if (data.isPeriodic && data.expirationDate) {
        const spendingDay = new Date(data.date).getDate();
        const expirationDay = new Date(data.expirationDate).getDate();
        return spendingDay === expirationDay;
      }
      return true;
    }, {
      path: ["expirationDate"],
      message: t("domains.spending.errors.expirationDate.sameDay"),
    });

export type SpendingData = z.infer<ReturnType<typeof createSpendingSchema>>;
