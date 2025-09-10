import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";

import Picker from '@emoji-mart/react';

import { Button } from '@/app/domains/shared/components/button';
import type { CategoryData } from "../schemas/Category";
import { Label } from "@/app/domains/shared/components/label";
import { Input } from "@/app/domains/shared/components/input";
import { useTranslation } from "react-i18next";
import { getEmojiById } from "./EmojiFunctions";
import { editCategory } from "../application/CategoryService";
import { deleteCategory } from "../application/CategoryService";
import { toast } from "sonner";
import { Dialog, DialogTrigger, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogClose, DialogTitle } from "@/app/domains/shared/components/dialog";
import { CategoryCombobox } from "./CategoryCombobox";

type ModifyCategoryFormProps = {
  categories: CategoryData[];
  refreshCategories: () => Promise<void>;
};

export function EditCategoryForm({ categories, refreshCategories }: ModifyCategoryFormProps) {
  const { t } = useTranslation();
  const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);
  const [selectedEmoji, setSelectedEmoji] = useState("");
  const [selectedIdEmoji, setSelectedIdEmoji] = useState("");
  const [emojiIsNative, setEmojiIsNative] = useState(false);
  const {
    register,
    handleSubmit,
    setValue,
    reset,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<CategoryData>();

  const watchedName = watch("name");

  useEffect(() => {
    if (selectedCategory) {
      setValue("name", selectedCategory.name);
    }
  }, [selectedCategory, setValue]);

  const onSubmit = async (formData: CategoryData) => {
    try {
      const requestBody: CategoryData = {
        ...formData,
        icon: selectedIdEmoji,
      };
      await editCategory(selectedCategory?.id, requestBody);

      const nativeEmoji = getEmojiById(selectedIdEmoji);
      toast.success(t('domains.category.modify.success', {icon: nativeEmoji, name: requestBody.name}));

      await refreshCategories();
      setSelectedCategory(null);
      setSelectedEmoji("");
      setSelectedIdEmoji("");
      reset();
    } catch (error) {
      toast.error(t('domains.category.modify.error'));
    }
  };
  
  const handleDelete = async () => {
    if (!selectedCategory) return;

    try {
      await deleteCategory(selectedCategory?.id);
      toast.success(t('domains.category.delete.success', { icon: getEmojiById(selectedCategory?.icon), name: selectedCategory?.name }));

      await refreshCategories();
      setSelectedCategory(null);
      setSelectedEmoji("");
      setSelectedIdEmoji("");
      reset();
    } catch (error) {
      toast.error(t('domains.category.delete.error'));
    }
  };

  return (
    <>
      <div className='form-container-responsive'>
        <form className="space-y-6">
          {/* Selector de categoría - Ancho completo centrado */}
          <div className="flex justify-center">
            <div className="w-full max-w-md">
              <CategoryCombobox
                categories={categories}
                selectedCategory={selectedCategory}
                setSelectedCategory={setSelectedCategory}
                setSelectedEmoji={setSelectedEmoji}
                setSelectedIdEmoji={setSelectedIdEmoji}
                setEmojiIsNative={setEmojiIsNative}
                disabled={false}
              />
            </div>
          </div>

          {/* Layout responsive para campos */}
          <div className="flex flex-col lg:flex-row gap-6">
            {/* Campo Nombre - Más ancho en desktop */}
            <div className="w-full lg:w-2/5">
              <Label htmlFor="name" className="label">{t('domains.category.name')}</Label>
              <Input 
                id="name" 
                {...register("name", { required: t('domains.category.errors.name.required') })} 
                className="input-form" 
                disabled={!selectedCategory} 
              />
              {errors.name && (
                <p className="text-red-500 text-sm mt-1">{errors.name.message}</p>
              )}
            </div>

            {/* Selector de Icono - Contenedor con mejor control */}
            <div className="w-full lg:w-3/5">
              <Label className="label">{t('domains.category.icon')}</Label>
              
              {/* Emoji seleccionado */}
              <div className="flex items-center gap-2 mb-4">
                <span className="text-2xl">
                  {t('domains.category.add.iconSelected')}
                  {emojiIsNative ? selectedEmoji : getEmojiById(selectedEmoji)}
                </span>
              </div>

              {/* Contenedor del picker con mejor control de overflow */}
              <div className="w-full max-w-full overflow-hidden">
                <div className="picker-container">
                  <Picker
                    onEmojiSelect={(emoji: any) => {
                      setSelectedEmoji(emoji.native);
                      setEmojiIsNative(true);
                      setSelectedIdEmoji(emoji.id);
                      setValue("icon", emoji.id);
                    }}
                    previewPosition="none"
                    theme="dark"
                  />
                </div>
              </div>
            </div>
          </div>

          <input type="hidden" {...register("icon")} />

          {/* Botones de acción */}
          <div className="flex flex-col sm:flex-row justify-center items-center gap-4 mt-8">
            {/* Botón Modificar */}
            <Dialog>
              <DialogTrigger asChild disabled={!selectedCategory || isSubmitting}>
                <Button className="button-neutral">
                  {t('domains.category.modify.submit')}
                </Button>
              </DialogTrigger>

              <DialogContent className="dialog-content">
                <form onSubmit={handleSubmit(onSubmit)}>
                  <DialogHeader className="dialog-header">
                    <DialogTitle className="dialog-title">
                      {t('domains.category.modify.dialog.title')}
                    </DialogTitle>
                    <DialogDescription className="dialog-description">
                      {t('domains.category.modify.dialog.description', { 
                        icon: getEmojiById(selectedCategory?.icon), 
                        name: selectedCategory?.name, 
                        newIcon: getEmojiById(selectedIdEmoji), 
                        newName: watchedName 
                      })}
                    </DialogDescription>
                  </DialogHeader>
                  <DialogFooter className="dialog-footer">
                    <DialogClose asChild>
                      <Button type="submit" className="button-green" disabled={isSubmitting}>
                        {t('domains.category.modify.dialog.confirm')}
                      </Button>
                    </DialogClose>
                    <DialogClose asChild>
                      <Button type="button" variant="secondary" className="button-red">
                        {t('domains.category.modify.dialog.cancel')}
                      </Button>
                    </DialogClose>
                  </DialogFooter>
                </form>
              </DialogContent>
            </Dialog>

            {/* Botón Eliminar */}
            <Dialog>
              <DialogTrigger asChild disabled={!selectedCategory || isSubmitting}>
                <Button className="button-neutral">
                  {t('domains.category.delete.submit')}
                </Button>
              </DialogTrigger>

              <DialogContent className="dialog-content">
                <form onSubmit={handleSubmit(handleDelete)}>
                  <DialogHeader className="dialog-header">
                    <DialogTitle className="dialog-title">
                      {t('domains.category.delete.dialog.title')}
                    </DialogTitle>
                    <DialogDescription className="dialog-description">
                      {t('domains.category.delete.dialog.description', { 
                        icon: getEmojiById(selectedCategory?.icon), 
                        name: selectedCategory?.name, 
                        newIcon: getEmojiById(selectedIdEmoji), 
                        newName: watchedName 
                      })}
                    </DialogDescription>
                  </DialogHeader>
                  <DialogFooter className="dialog-footer">
                    <DialogClose asChild>
                      <Button type="submit" className="button-green" disabled={isSubmitting}>
                        {t('domains.category.delete.dialog.confirm')}
                      </Button>
                    </DialogClose>
                    <DialogClose asChild>
                      <Button type="button" variant="secondary" className="button-red">
                        {t('domains.category.delete.dialog.cancel')}
                      </Button>
                    </DialogClose>
                  </DialogFooter>
                </form>
              </DialogContent>
            </Dialog>
          </div>
        </form>
      </div>
    </>
  );
}