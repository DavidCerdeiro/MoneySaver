import { z } from "zod";
import { createAccountSchema } from "../../account/schemas/Account";
 
export const createExtractTransactionSchema = (t: any) => z.object({
  account: createAccountSchema().optional(),
  code: z.string().optional(),
  minDate: z.string().min(1, { message: t('errors.requiredField') }),
  maxDate: z.string().min(1, { message: t('errors.requiredField') }),
}).refine((data) => {
  const min = new Date(data.minDate);
  const now = new Date();

  const firstOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);

  return min >= firstOfMonth;
}, {
  message: t('domains.transaction.errors.minDate.currentMonth'),
  path: ['minDate'],
}).refine((data) => {
  const max = new Date(data.maxDate);
  const now = new Date();

  return max <= now;
}, {
  message: t('domains.transaction.errors.maxDate.future'),
  path: ['maxDate'],
}).refine((data) => {
  const min = new Date(data.minDate);
  const max = new Date(data.maxDate);

  return min <= max;
}, {
  message: t('domains.transaction.errors.minDate.cannotBeAfterMaxDate'),
  path: ['minDate'],
});

export type ExtractTransactionData = z.infer<ReturnType<typeof createExtractTransactionSchema>>;
