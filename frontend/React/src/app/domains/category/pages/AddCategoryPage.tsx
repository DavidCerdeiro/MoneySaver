import { CategoryPageLayout } from "../../shared/layouts/CategoryPageLayout";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { AddCategoryForm } from "../components/AddCategoryForm";
export function AddCategoryPage() {

  return (
    <CategoryPageLayout>
      <AddCategoryForm />
    </CategoryPageLayout>
  )
}
