import { z } from "zod";

export const EstablishmentDataSchema = z.object({
  id: z.number().min(0),
  name: z.string().min(2).max(100),
  country: z.string().min(2).max(100),
  city: z.string().min(2).max(100)
});

export type EstablishmentData = z.infer<typeof EstablishmentDataSchema>;
