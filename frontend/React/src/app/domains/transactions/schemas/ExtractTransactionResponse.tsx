import { z } from "zod";

export const transactionSchema = z.object({
  account: z.any(),
  trueLayerId: z.string(),
});

const spendingSchema = z.object({
  amount: z.number(),
  name: z.string().nullable().optional(),
  establishment: z.any(),
  date: z.string(),
});

export const createExtractTransactionsResponseSchema = z.object({
  transactions: z.array(z.tuple([transactionSchema, spendingSchema])),
});

export type ExtractTransactionResponse = z.infer<typeof createExtractTransactionsResponseSchema>;
