import { useTranslation } from 'react-i18next';
import { CategoriesTable } from "../components/CategoriesTable";
import { EditCategoryForm } from "../components/EditCategoryForm";
import { useEffect, useState } from "react";
import type { CategoryData } from "../schemas/Category";
import { getCategories } from "../application/CategoryService";
import { CategoryPageLayout } from "../../shared/layouts/CategoryPageLayout";

export function EditCategoryPage() {
  const { t } = useTranslation();
  const [categories, setCategories] = useState<CategoryData[]>([]);
  const refreshCategories = async () => {
    try {
      const data = await getCategories();
      setCategories(data.categories);
    } catch (error) {
      console.error("Error refreshing categories", error);
    }
  };

  useEffect(() => {
    refreshCategories();
  }, []);

  return (
    <CategoryPageLayout>
      <div className="edit-category-page">
        <h1 className="page-title">{t('header.sections.spendings.editCategory.title')}</h1>
        <p className="page-description">{t('domains.category.modify.description')}</p>
        <CategoriesTable categories={categories} />
        <div className="mt-5">
          <EditCategoryForm categories={categories} refreshCategories={refreshCategories} />
        </div>
      </div>
    </CategoryPageLayout>
  );
} 