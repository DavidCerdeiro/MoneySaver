import {
  Table,
  TableBody,
  TableCell,
  TableCaption,
  TableHead,
  TableHeader,
  TableRow,
} from "@/app/domains/shared/components/table";
import { useTranslation } from "react-i18next";
import type { TransactionResponse } from "../schemas/TransactionResponse";
import { useMemo, useState } from "react";
import { getEmojiById } from "../../category/components/EmojiFunctions";
import { ArrowUp, ArrowDown } from "lucide-react";
type TransactionTableProps = {
  transactions: TransactionResponse[];
};

type SortableKeys = keyof TransactionResponse;
type SortDirection = "ascending" | "descending";
type SortConfig = {
  key: SortableKeys;
  direction: SortDirection;
};

export function TransactionsTable({ transactions }: TransactionTableProps) {
  const { t } = useTranslation();
  
  const [sortConfig, setSortConfig] = useState<SortConfig>({
    key: 'date',
    direction: 'descending',
  });

  const sortedTransactions = useMemo(() => {
    const sortableItems = [...transactions];

    sortableItems.sort((a, b) => {
      const aValue = a[sortConfig.key];
      const bValue = b[sortConfig.key];

      if (aValue == null) return 1; 
      if (bValue == null) return -1; 
      
      if (aValue < bValue) {
        return sortConfig.direction === 'ascending' ? -1 : 1;
      }
      if (aValue > bValue) {
        return sortConfig.direction === 'ascending' ? 1 : -1;
      }
      
      return 0;
    });

    return sortableItems;
  }, [transactions, sortConfig]);

  const requestSort = (key: SortableKeys) => {
    let direction: SortDirection = 'ascending';
    if (sortConfig.key === key && sortConfig.direction === 'ascending') {
      direction = 'descending';
    }
    setSortConfig({ key, direction });
  };

  const getSortIcon = (columnKey: SortableKeys) => {
    if (sortConfig.key !== columnKey) return null;
    return sortConfig.direction === 'ascending' 
      ? <ArrowUp className="inline-block ml-2 h-4 w-4" /> 
      : <ArrowDown className="inline-block ml-2 h-4 w-4" />;
  };

  return (
    <div>
      <Table>
          <TableCaption className="text-white">
              {t('domains.transaction.table.caption')}
          </TableCaption>
          <TableHeader>
              <TableRow>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('categoryName')}>
                      {t('domains.spending.category')}
                      {getSortIcon('categoryName')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('accountName')}>
                      {t('domains.account.title')}
                      {getSortIcon('accountName')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('accountNumber')}>
                      {t('domains.account.number')}
                      {getSortIcon('accountNumber')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('name')}>
                      {t('domains.spending.name')}
                      {getSortIcon('name')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('amount')}>
                      {t('domains.spending.amount')}
                      {getSortIcon('amount')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('date')}>
                      {t('domains.spending.date')}
                      {getSortIcon('date')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('establishmentName')}>
                      {t('domains.establishment.title')}
                      {getSortIcon('establishmentName')}
                  </TableHead>
              </TableRow>
          </TableHeader>
          <TableBody>
              {sortedTransactions.map((transaction) => (
                  <TableRow key={transaction.id}>
                      <TableCell className="table-cell">
                          {getEmojiById(transaction.categoryIcon)} {transaction.categoryName === "Deleted" ? t('domains.category.deleted') : transaction.categoryName}
                      </TableCell>
                      <TableCell className="table-cell">{transaction.accountName}</TableCell>
                      <TableCell className="table-cell">{transaction.accountNumber}</TableCell>
                      <TableCell className="table-cell">{transaction.name}</TableCell>
                      <TableCell className="table-cell">{transaction.amount}€</TableCell>
                      <TableCell className="table-cell">{transaction.date.substring(0, 10)}</TableCell>
                      <TableCell className="table-cell">{transaction.establishmentName || '-'}</TableCell>
                  </TableRow>
              ))}
          </TableBody>
      </Table>
    </div>
  );
}