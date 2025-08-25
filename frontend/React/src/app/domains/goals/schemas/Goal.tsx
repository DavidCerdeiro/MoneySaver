import { z } from "zod";

export const createGoalSchema = (t: (key: string) => string) => z.object({
  id: z.number().optional(),
  name: z.string().min(1, { message: t('errors.requiredField') }).max(100),
  targetAmount: z.number().min(0, { message: t('errors.requiredField') }),
  idCategory: z.number().min(1, { message: t('errors.requiredField') }),
  nameCategory: z.string().optional(),
  amountCategory: z.number().optional(),
  percent: z.number().optional(),
});

export type GoalData = z.infer<ReturnType<typeof createGoalSchema>>;