import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/app/domains/shared/components/table";
import { useTranslation } from "react-i18next";
import type { GoalData } from "../schemas/Goal";
type GoalsTableProps = {
  goals: GoalData[];
  month?: number;
  year?: number;
  isView: boolean;
};
function getPercentColor(percent: number) {
  if (percent <= 25) return "text-green-500";
  if (percent <= 50) return "text-yellow-400";
  if (percent <= 75) return "text-orange-400";
  if (percent <= 100) return "text-red-500";
  return "text-red-700 font-bold"; // > 100
}

export function GoalsTable({ goals, month, year, isView }: GoalsTableProps) {
  const { t } = useTranslation();

  return (
    <div className="flex flex-col items-center justify-center px-4 mb-5">
      <Table>
        <TableCaption className="text-white">
          {isView ? t('domains.goal.view.tableCaption', { month, year }) : t('domains.goal.add.tableCaption')}
        </TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="text-left text-white">
              {t('domains.goal.name')}
            </TableHead>
            <TableHead className="text-left text-white">
              {t('domains.goal.category')}
            </TableHead>
            <TableHead className="text-left text-white">
              {t('domains.category.totalSpending')}
            </TableHead>
            <TableHead className="text-left text-white">
              {t('domains.goal.targetAmount')}
            </TableHead>
            <TableHead className="text-left text-white">
              {t('domains.goal.percent')}
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {goals.map((goal) => (
            <TableRow key={goal.id}>
              <TableCell className="font-medium text-white">
                <div className="text-left text-white">
                  <span>{goal.name}</span>
                </div>
              </TableCell>
              <TableCell className="text-left text-white">
                {goal.nameCategory}
              </TableCell>
              <TableCell className="text-left text-white">
                {goal.amountCategory?.toFixed(2)}€
              </TableCell>
              <TableCell className="text-left text-white">
                {goal.targetAmount?.toFixed(2)}€
              </TableCell>
              <TableCell className={`text-left ${getPercentColor(goal.percent ?? 0)}`}>
                {goal.percent?.toFixed(2)}%
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}