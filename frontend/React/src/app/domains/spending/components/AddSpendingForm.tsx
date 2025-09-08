import { CategoryCombobox } from "../../category/components/CategoryCombobox";
import { useTranslation } from 'react-i18next';
import { useState, useEffect, useRef, type ChangeEvent } from 'react';
import type { CategoryData } from "../../category/schemas/Category";
import { Label } from "../../shared/components/label";
import { Input } from "../../shared/components/input";
import type { SpendingData } from "../schemas/Spending";
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { createSpendingSchema } from "../schemas/Spending";
import { Button } from "../../shared/components/button";
import type { TypePeriodicData } from "../schemas/TypePeriodic";
import { TypePeriodicCombobox } from "./TypePeriodicCombobox";
import { EstablishmentCombobox } from "./EstablishmentCombobox";
import { addSpending, processFileDirect } from "../application/SpendingService";
import { toast } from "sonner";
import type { EstablishmentData } from "../schemas/Establishment";
import { Separator } from "../../shared/components/separator";
import { uploadBill } from "../application/BillService";

type ProcessFileResponse = {
    receiptDate: string | null;
    totalAmount: number | null;
    idEstablishment: number;
    establishmentName: string;
    idCategory: number | null;
};

type AddSpendingFormProps = {
    categories: CategoryData[];
    typePeriodic: TypePeriodicData[];
    establishments: EstablishmentData[];
    loadEstablishment: () => void;
};

export function AddSpendingForm({ categories, typePeriodic, establishments, loadEstablishment }: AddSpendingFormProps) {
    const { t } = useTranslation();
    const schema = createSpendingSchema(t);
    // Refers to the file input element
    const fileInputRef = useRef<HTMLInputElement>(null);

    const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);
    const [selectedTypePeriodic, setSelectedTypePeriodic] = useState<TypePeriodicData | null>(null);
    const [selectedEstablishment, setSelectedEstablishment] = useState<EstablishmentData | null>(null);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileSubmitted, setFileSubmitted] = useState(false);
    const [isProcessing, setIsProcessing] = useState(false);

    const {
        register,
        handleSubmit,
        reset,
        watch,
        setValue,
        formState: { errors, isSubmitting },
    } = useForm<SpendingData>({
        resolver: zodResolver(schema),
        defaultValues: {
            name: '',
            amount: undefined,
            isPeriodic: false,
            establishment: undefined,
        },
    });

    useEffect(() => {
        selectedTypePeriodic && setValue("typePeriodic", selectedTypePeriodic.id);
        selectedCategory && setValue("idCategory", selectedCategory?.id || 0);
        if (selectedEstablishment) {
            if (selectedEstablishment.id === 0) {
                // If it's a new establishment, we need to set its name from the form
                const currentEstablishment = watch("establishment");
                setValue("establishment", { 
                    ...selectedEstablishment, 
                    name: currentEstablishment?.name === "+ Añadir nuevo" ? currentEstablishment?.name : "", 
                });
            } else {
                setValue("establishment", selectedEstablishment);
            }
        } else {
            setValue("establishment", undefined);
        }
    }, [selectedCategory, selectedTypePeriodic, selectedEstablishment, setValue]);

    const isPeriodic = watch("isPeriodic");

    const onSubmitForm = async (formData: SpendingData) => {
        if (formData.establishment) {
            const { name } = formData.establishment;
            if (!name) {
                formData.establishment = undefined;
            }
        }
        
        const spending = await addSpending(formData) as SpendingData;
        if (selectedFile && spending?.id) {
            await uploadBill(selectedFile, spending.id);
        }

        toast.success(t('domains.spending.add.success', { name: formData.name, amount: formData.amount, category: selectedCategory?.name }));

        loadEstablishment();
        setSelectedCategory(null);
        setSelectedTypePeriodic(null);
        setSelectedEstablishment(null);
        setSelectedFile(null);
        setFileSubmitted(false);
        reset();
        if (fileInputRef.current) {
            fileInputRef.current.value = "";
        }
    };

    const onSubmitFile = async (file: File) => {
        setIsProcessing(true);
        try {
            const formDataWithFile = new FormData();
            formDataWithFile.append('file', file);
            const result: ProcessFileResponse = await processFileDirect(formDataWithFile);
            
            toast.info(t('domains.spending.add.fileProcessed'));

            setValue('name', result.establishmentName ? t('domains.spending.add.defaultSpendingProcessed', { establishment: result.establishmentName }) : '');
            setValue('amount', result.totalAmount ?? 0);
            setValue('date', result.receiptDate || '');
            if(result.idCategory){
                setValue('idCategory', result.idCategory);
                setSelectedCategory(categories.find(cat => cat.id === result.idCategory) || null);
            }
            setSelectedEstablishment({ id: result.idEstablishment, name: result.establishmentName || '' });
            const newEstablishment: EstablishmentData = {
                id: result.idEstablishment,
                name: result.establishmentName || ''
            };

            setSelectedEstablishment(newEstablishment);
            setValue("establishment", newEstablishment);
            setFileSubmitted(true);
        } catch (error) {
            toast.error(t('domains.spending.add.fileError'));
        } finally {
            setIsProcessing(false);
        }
    };

    const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
        // Reset the form when a new file is selected
        const file = e.target.files?.[0];
        setSelectedFile(file || null);

        if (file) {
            reset();
            setSelectedCategory(null);
            setSelectedTypePeriodic(null);
            setSelectedEstablishment(null);
        }
    };

    useEffect(() => {
        if (selectedCategory) {
            setValue("idCategory", selectedCategory?.id || 0);
        } else {
            setValue("idCategory", 0);
        }
    }, [selectedCategory, setValue]);
    
    return (
        <div className="form-container">
            <form onSubmit={handleSubmit(onSubmitForm)} className="space-y-6">
                
                {/* Primera fila: Nombre y Cantidad */}
                <div className="row-input">
                    <div className="w-full">
                        <Label htmlFor="name" className="label">{t('domains.spending.name')}</Label>
                        <Input 
                            id="name" 
                            placeholder={t('domains.spending.namePlaceholder')} 
                            {...register('name')} 
                            className="input-form" 
                            disabled={isProcessing} 
                        />
                        {errors.name && <p className="text-red-500 text-xs mt-1">{errors.name.message}</p>}
                    </div>
                    <div className="w-full">
                        <Label htmlFor="amount" className="label">{t('domains.spending.add.amount')}</Label>
                        <Input
                            type="number"
                            step="any"
                            inputMode="decimal"
                            id="amount"
                            placeholder={t('domains.spending.amountPlaceholder')}
                            {...register('amount', { valueAsNumber: true })}
                            className="input-form"
                            disabled={isProcessing}
                        />
                        {errors.amount && <p className="text-red-500 text-xs mt-1">{errors.amount.message}</p>}
                    </div>
                </div>

                {/* Segunda fila: Categoría y Fecha */}
                <div className="row-input">
                    <div className="w-full">
                        <Label htmlFor="category" className="label">{t('domains.spending.category')}</Label>
                        <CategoryCombobox
                            categories={categories}
                            selectedCategory={selectedCategory}
                            setSelectedCategory={setSelectedCategory}
                            disabled={isProcessing}
                            setSelectedEmoji={() => {}} 
                            setSelectedIdEmoji={() => {}} 
                            setEmojiIsNative={() => {}} 
                        />
                        {errors.idCategory && <p className="text-red-500 text-xs mt-1">{errors.idCategory.message}</p>}
                    </div>
                    <div className="w-full">
                        <Label htmlFor="date" className="label">{t('domains.spending.date')}</Label>
                        <Input 
                            id="date" 
                            type="date" 
                            {...register("date")} 
                            className="input-form" 
                            disabled={isProcessing} 
                        />
                        {errors.date && <p className="text-red-500 text-xs mt-1">{errors.date.message}</p>}
                    </div>
                </div>

                {/* Tercera fila: Establecimiento */}
                <div className="row-input">
                    <div className="w-full">
                        <Label htmlFor="establishment" className="label">{t('domains.establishment.title')}</Label>
                        <EstablishmentCombobox
                            establishments={establishments}
                            selectedEstablishment={selectedEstablishment}
                            setSelectedEstablishment={setSelectedEstablishment}
                            disabled={isProcessing}
                        />
                    </div>
                    <div className="w-full">
                        <Label htmlFor="establishmentName" className="label">{t('domains.establishment.name')}</Label>
                        <Input 
                            id="establishmentName" 
                            type="text" 
                            className="input-form" 
                            disabled={!selectedEstablishment || selectedEstablishment.id !== 0 || isProcessing} 
                            {...register("establishment.name")} 
                            placeholder={t('domains.establishment.namePlaceholder')} 
                        />
                    </div>
                </div>

                {/* Cuarta fila: Periodicidad */}
                <div className="row-three-input">
                    <div className="w-full">
                        <div className="flex items-center gap-3 min-h-[44px]">
                            <input 
                                type="checkbox" 
                                id="isPeriodic" 
                                {...register("isPeriodic")} 
                                className="w-5 h-5 text-green-600 bg-zinc-800 border-zinc-600 rounded focus:ring-green-500 focus:ring-2" 
                                disabled={isProcessing} 
                            />
                            <Label htmlFor="isPeriodic" className="responsive-text-sm cursor-pointer">
                                {t('domains.spending.periodicity')}
                            </Label>
                        </div>
                    </div>
                    <div className="w-full">
                        <Label htmlFor="typePeriodic" className="label">{t('domains.typePeriodic.title')}</Label>
                        <TypePeriodicCombobox
                            typePeriodic={typePeriodic}
                            selectedTypePeriodic={selectedTypePeriodic}
                            setSelectedTypePeriodic={setSelectedTypePeriodic}
                            disabled={!isPeriodic || isProcessing}
                        />
                        {errors.typePeriodic && <p className="text-red-500 text-xs mt-1">{errors.typePeriodic.message}</p>}
                    </div>
                    <div className="w-full">
                        <Label htmlFor="expirationDate" className="label">{t('domains.spending.expirationDate')}</Label>
                        <Input 
                            disabled={!isPeriodic || isProcessing} 
                            id="expirationDate" 
                            type="date" 
                            {...register("expirationDate")} 
                            className="input-form" 
                        />
                        {errors.expirationDate && <p className="text-red-500 text-xs mt-1">{errors.expirationDate.message}</p>}
                    </div>
                </div>

                {/* Botón de envío */}
                <div className="button-container">
                    <Button 
                        type="submit" 
                        disabled={isSubmitting || isProcessing}
                        className="button-green"
                    >
                        {isSubmitting ? t('domains.spending.add.submitting') : t('domains.spending.add.submit')}
                    </Button>
                </div>
            </form>

            <Separator className="my-6" />

            {/* Formulario de archivo */}
            <form onSubmit={(e) => {
                e.preventDefault();
                if (selectedFile) {
                    onSubmitFile(selectedFile);
                }
            }}>
                <p className="responsive-text-sm text-gray-300 mb-4">
                    {t('domains.spending.add.fileDescription')}
                </p>
                
                <div className="flex flex-col sm:flex-row sm:items-end gap-4">
                    <div className="flex-1">
                        <Label htmlFor="file" className="label">{t('domains.spending.add.labelFile')}</Label>
                        <Input 
                            type="file" 
                            onChange={handleFileChange} 
                            disabled={isProcessing} 
                            ref={fileInputRef}
                            className="input-form"
                            accept=".pdf,.jpg,.jpeg,.png,.gif"
                        />
                    </div>
                    <div className="sm:flex-shrink-0">
                        <Button 
                            type="submit" 
                            disabled={!selectedFile || isProcessing || fileSubmitted}
                            className="button-neutral w-full sm:w-auto"
                        >
                            {isProcessing ? t('domains.spending.add.processingFile') : t('domains.spending.add.submitFile')}
                        </Button>
                    </div>
                </div>
            </form>
        </div>
    );
}