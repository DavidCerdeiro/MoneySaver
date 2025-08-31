// AddTransactionForm.tsx
import { useTranslation } from "react-i18next";
import { Button } from "../../shared/components/button";
import { Input } from "../../shared/components/input";
import { useForm } from "react-hook-form";
import { createAddTransactionSchema, type AddTransactionData } from "../schemas/AddTransactionData";
import { zodResolver } from "@hookform/resolvers/zod/dist/zod.js";
import { Label } from "../../shared/components/label";
import { useEffect, useState } from "react";
import type { CategoryData } from "../../category/schemas/Category";
import { CategoryCombobox } from "../../category/components/CategoryCombobox";
import type { EstablishmentData } from "../../spending/schemas/Establishment";
import { EstablishmentCombobox } from "../../spending/components/EstablishmentCombobox";
import { addTransaction } from "../application/TransactionService";
import { toast } from "sonner";

type ExtractTransactionsFormProps = {
  response: [transaction: any, spending: any]; // cada elemento es una tupla
  categories: CategoryData[];
  establishments: EstablishmentData[];
  onSubmitted: () => void;
};

export function AddTransactionForm({ response, categories, establishments, onSubmitted }: ExtractTransactionsFormProps) {
  const { t } = useTranslation();
  const schema = createAddTransactionSchema(t);
  const [transaction, spending] = response;
  const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);
  const [selectedEstablishment, setSelectedEstablishment] = useState<EstablishmentData | null>(null);
  const {
    register,
    handleSubmit,
    setValue,
    watch,
    formState: { errors },
  } = useForm<AddTransactionData>({
    resolver: zodResolver(schema),
    defaultValues: {
      transaction: {
        account: transaction.account,
        trueLayerId: transaction.trueLayerId,
      },
      spending: {
        amount: spending.amount,
        name: spending.name ?? "",
        date: spending.date,
        establishment: spending.establishment ?? "",
        isPeriodic: false,
      },
    },
  });
  
  useEffect(() => {
    if (selectedCategory)
      setValue("spending.idCategory", selectedCategory?.id || 0);
    else
      setValue("spending.idCategory", 0);
    if(selectedEstablishment){
      if (selectedEstablishment.id === 0) {
                // If it's a new establishment, we need to set its name from the form
                const currentEstablishment = watch("spending.establishment");
                setValue("spending.establishment", { 
                    ...selectedEstablishment, 
                    name: currentEstablishment?.name || selectedEstablishment.name || "" 
                });
            } else {
                setValue("spending.establishment", selectedEstablishment);
            }
    }
  }, [selectedCategory, selectedEstablishment, setValue]);

  const onSubmitForm = async (data: AddTransactionData) => {
    console.log("Datos del formulario:", data);
    const result = await addTransaction(data);
    toast.success(t("domains.transaction.add.success", { name: result.name, amount: result.amount }));
    onSubmitted();
  };

  return (
    <form onSubmit={handleSubmit(onSubmitForm)} className="space-y-4">
      <div className="row-input">
        <div className="w-full">
          <Label htmlFor="name" className="label">{t("domains.spending.name")}</Label>
          <Input id="name" type="text" {...register("spending.name")} className="input-dark" defaultValue={spending.name ?? ""}/>
          {errors.spending?.name && (
            <p className="text-red-500 text-sm">{errors.spending.name.message}</p>
          )}
        </div>
        <div className="w-full">
          <Label htmlFor="category" className="label">{t("domains.spending.category")}</Label>
          <CategoryCombobox
            categories={categories}
            selectedCategory={selectedCategory}
            setSelectedCategory={setSelectedCategory}
            disabled={false}
            setSelectedEmoji={() => {}}
            setSelectedIdEmoji={() => {}}
            setEmojiIsNative={() => {}}
          />
          {errors.spending?.idCategory && (
            <p className="text-red-500 text-sm">{errors.spending.idCategory.message}</p>
          )}
        </div>
      </div>

      <div className="row-three-input mt-10">
        <div className="w-full">
          <Label htmlFor="amount" className="label">{t("domains.spending.amount")}</Label>
          <Input id="amount" type="number" {...register("spending.amount")} className="input-dark" disabled={true} />
        </div>
        <div className="w-full">
          <Label htmlFor="date" className="label">{t("domains.spending.date")}</Label>
          <Input id="date" type="date" {...register("spending.date")} className="input-dark" readOnly disabled={true} />
        </div>
        <div className="w-full">
          <Label htmlFor="account" className="label">{t("domains.account.title")}</Label>
          <Input
            id="account"
            type="text"
            value={transaction.account?.name ?? ""}
            className="input-dark"
            disabled={true}
          />
        </div>
      </div>
      <div className="row-input mt-10">
          <div className="w-full">
            <Label htmlFor="establishment" className="label">{t("domains.establishment.title")}</Label>
            <EstablishmentCombobox
              establishments={establishments}
              selectedEstablishment={selectedEstablishment}
              setSelectedEstablishment={setSelectedEstablishment}
              disabled={false}
            />
          </div>
          <div className="w-full">
            <Label htmlFor="establishment" className="label">{t('domains.establishment.name')}</Label>
            <Input id="establishment" type="text" className="input-dark" disabled={!selectedEstablishment || selectedEstablishment.id !== 0} {...register("spending.establishment.name")} placeholder={t('domains.establishment.namePlaceholder')} />
          </div>
      </div>
      <div className="flex justify-center">
        <Button type="submit" className="btn-primary">{t("domains.transaction.add.submit")}</Button>
      </div>
    </form>
  );
}
